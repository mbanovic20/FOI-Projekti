package hr.foi.rampu.snackalchemist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.dataClases.Order

class OrdersAdapter(private val ordertList:ArrayList<Order>):RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {
    inner class OrdersViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val orderDatum: TextView
        private val orderCijena: TextView
        private val orderRecyclerView: RecyclerView
        val pogled = view
        init {

            orderDatum = view.findViewById(R.id.orderDateText)
            orderCijena = view.findViewById(R.id.totalPriceText)
            orderRecyclerView =view.findViewById(R.id.itemsRecyclerView)



        }
        fun bind(order: Order) {
            orderDatum.text = order.Datum
            orderCijena.text = order.Cijena.toString()
            Log.d("lista", order.proizvodi.toString())
            orderRecyclerView.adapter = ProductOrdersAdapter(order.proizvodi!!)
            orderRecyclerView.layoutManager = LinearLayoutManager(pogled.context)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val CatalogView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.orderlayout, parent, false)
        return OrdersViewHolder(CatalogView)
    }
    override fun getItemCount() = ordertList.size
    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(ordertList[position])
    }


}