package com.omnicuris.quizapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.omnicuris.quizapp.R;
import com.omnicuris.quizapp.models.QuestionTO;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private QuestionTO questionTO;
    private String mParam2;
    private RadioGroup rbgQuestion;
    RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private OnFragmentInteractionListener mListener;

    public RadioGroup getRbgQuestion() {
        return rbgQuestion;
    }

    public void setRbgQuestion(RadioGroup rbgQuestion) {
        this.rbgQuestion = rbgQuestion;
    }

    public RadioButton getRbOption1() {
        return rbOption1;
    }

    public void setRbOption1(RadioButton rbOption1) {
        this.rbOption1 = rbOption1;
    }

    public RadioButton getRbOption2() {
        return rbOption2;
    }

    public void setRbOption2(RadioButton rbOption2) {
        this.rbOption2 = rbOption2;
    }

    public RadioButton getRbOption3() {
        return rbOption3;
    }

    public void setRbOption3(RadioButton rbOption3) {
        this.rbOption3 = rbOption3;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(QuestionTO param1, String param2) {
        QuestionFragment fragment = null;
        try {
            fragment = new QuestionFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        } catch (Exception ex) {
            Log.d("log", "Exception in QuestionFragment::newInstance()" + ex);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                questionTO = (QuestionTO) getArguments().getSerializable(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        } catch (Exception ex) {
            Log.d("log", "Exception in QuestionFragment::onCreate()" + ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_question, container, false);
            TextView tvQuestion = view.findViewById(R.id.tvQuestion);
            tvQuestion.setText(questionTO.getQuestion());
            rbOption1 = view.findViewById(R.id.rbOption1);
            rbOption2 = view.findViewById(R.id.rbOption2);
            rbOption3 = view.findViewById(R.id.rbOption3);
            rbOption4 = view.findViewById(R.id.rbOption4);

            rbOption1.setText(questionTO.getOptions().get(0));
            rbOption2.setText(questionTO.getOptions().get(1));
            rbOption3.setText(questionTO.getOptions().get(2));
            rbOption4.setText(questionTO.getOptions().get(3));
            rbgQuestion = view.findViewById(R.id.rbgQuestion);

            return view;
        } catch (Exception ex) {
            Log.d("log", "Exception in QuestionFragment::onCreateView()" + ex);
        }
        return null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public RadioButton getRbOption4() {
        return rbOption4;
    }

    public void setRbOption4(RadioButton rbOption4) {
        this.rbOption4 = rbOption4;
    }
}