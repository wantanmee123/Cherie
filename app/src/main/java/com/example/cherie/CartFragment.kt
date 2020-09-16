package com.example.cherie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_cart.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var ref : DatabaseReference
    lateinit var cartList:MutableList<Item>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    private fun readFireStoreData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("cart")
            .get().addOnCompleteListener{

                val result: StringBuffer = StringBuffer()

                if(it.isSuccessful){
                    for(document in it.result!!){
                        result.append("Item Name: ").append(document.data.getValue("itemName")).append("\n")
                            .append("Price: ").append(document.data.getValue("price")).append("\n")
                            .append("Quantity: ").append(document.data.getValue("quantity")).append("\n\n")
                    }
                    showResult.setText(result)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readFireStoreData()

//        fun readFireStoreData(){
//            val db = FirebaseFirestore.getInstance()
//            db.collection("cart")
//                .get().addOnCompleteListener{
//
//                    val result: StringBuffer = StringBuffer()
//
//                    if(it.isSuccessful){
//                        for(document in it.result!!){
//                            result.append(document.data.getValue("itemName")).append(" ")
//                                .append(document.data.getValue("price")).append("\n\n")
//                                .append(document.data.getValue("quantity")).append("\n\n")
//                        }
//                        showResult.setText(result)
//                    }
//                }
//        }

//        val db = FirebaseFirestore.getInstance()
//
//        val docRef = db.collection("menu").document("cake1")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("exist", "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d("noexist", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("errordb", "get failed with ", exception)
//            }

//        cartList = mutableListOf()
//        listView = listView1
//        ref = FirebaseDatabase.getInstance().getReference("cart")
//
//        ref.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot!!.exists()){
//                    cartList.clear()
//                    for(e in snapshot.children){
//                        val cart = e.getValue(Item::class.java)
//                        cartList.add(cart!!)
//                    }
//
//                    val adapter = CartAdapter(requireContext(), R.layout.fragment_cart, cartList)
//                    listView.adapter = adapter
//                }
//            }
//
//        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}