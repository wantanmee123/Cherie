package com.example.cherie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.cherie.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_today_special.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TodaySpecialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodaySpecialFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    private fun saveFireStore(itemName: String, price: Double, quantity: Int){
        val db = FirebaseFirestore.getInstance()
        val cart: MutableMap<String, Any> = HashMap()
        cart["itemName"] = itemName
        cart["price"] = price
        cart["quantity"] = quantity

        db.collection("cart")
            .add(cart)
            .addOnSuccessListener {
                Toast.makeText(activity, "Record added successfully ", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(activity, "Record failed to add ", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_special, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSpecial.setOnClickListener{
            val itemName = "Special Menu"
            val price = 15.99
            val quantity = 1

            saveFireStore(itemName, price, quantity)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodaySpecialFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodaySpecialFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}