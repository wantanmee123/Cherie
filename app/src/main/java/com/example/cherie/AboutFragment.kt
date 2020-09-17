package com.example.cherie

import android.content.ClipData
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_cart.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import androidx.core.content.ContextCompat.getSystemService as getSystemService1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var ref : DatabaseReference
    lateinit var cartList:MutableList<ClipData.Item>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun saveStore(feedback:String){
        val sdf = SimpleDateFormat ("yyyy/MM/dd HH:mm:ss")
        val now = Date()
        val auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.email.toString()
        val fb = FirebaseFirestore.getInstance()
        val feedbackTable: MutableMap<String, Any?> = HashMap()
        feedbackTable["Desc"] = feedback
        feedbackTable["Date"] = sdf.format(now)
        feedbackTable["UserID"] = userID


        fb.collection("feedbackTable")
            .add(feedbackTable)
            .addOnSuccessListener {
                Toast.makeText(activity, "Feedback submitted successfully ", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(activity, "Feedback failed to submit ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSubmit.setOnClickListener() {
            val text = feedback_text.text.toString()
            saveStore(text)
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

       readStore()
    }


    private fun readStore() {
        val fb = FirebaseFirestore.getInstance()
        fb.collection("feedbackTable")
            .get().addOnCompleteListener{

                val result = StringBuffer()

                if(it.isSuccessful){
                    for(document in it.result!!){
                        result.append("Feedback : ").append(document.data.getValue("Desc")).append("\n")
                            .append("Date : ").append(document.data.getValue("Date")).append("\n")
                            .append("User : ").append(document.data.getValue("UserID")).append("\n\n")
                    }
                    txt1.setText(result)
                }
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}