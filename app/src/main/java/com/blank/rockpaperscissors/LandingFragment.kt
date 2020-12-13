package com.blank.rockpaperscissors

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LandingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listener: (CharSequence) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View? {
        return inflater.inflate(R.layout.fragment_landing , container , false)
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val tvLanding = view.findViewById<TextView>(R.id.tvLanding)
        val etName = view.findViewById<EditText>(R.id.etName)
        val ivLanding = view.findViewById<ImageView>(R.id.ivLanding)
        tvLanding.text = param1
        when (param2) {
            "1" -> {
                ivLanding.setImageResource(R.drawable.ic_landing_page1)
                etName.visibility = View.GONE
            }
            "2" -> {
                ivLanding.setImageResource(R.drawable.ic_landing_page2)
                etName.visibility = View.GONE
            }
            "3" -> {
                ivLanding.setImageResource(R.drawable.ic_landing_page3)
                etName.visibility = View.VISIBLE
                etName.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        p0: CharSequence? ,
                        p1: Int ,
                        p2: Int ,
                        p3: Int
                    ) {
                    }

                    override fun onTextChanged(
                        p0: CharSequence ,
                        p1: Int ,
                        p2: Int ,
                        p3: Int
                    ) {
                        listener(p0)
                    }

                    override fun afterTextChanged(p0: Editable?) {}
                })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            param1: String ,
            param2: String ,
            listener: (CharSequence) -> Unit
        ) =
            LandingFragment().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putString(ARG_PARAM1 , param1)
                    putString(ARG_PARAM2 , param2)
                }
            }
    }
}