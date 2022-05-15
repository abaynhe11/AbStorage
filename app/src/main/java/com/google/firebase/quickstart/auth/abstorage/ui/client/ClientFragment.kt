package com.google.firebase.quickstart.auth.abstorage.ui.client

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.quickstart.auth.abstorage.R
import com.google.firebase.quickstart.auth.buildPdf
import com.google.firebase.quickstart.auth.getFiles
import com.google.firebase.quickstart.auth.proposalExists
import kotlinx.android.synthetic.main.fragment_client.*
import java.io.File
import java.util.*

class ClientFragment : Fragment(), FilesAdapter.OnClickListener {
    private var adapter = FilesAdapter(this)
    private var clientId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        listFiles.layoutManager = LinearLayoutManager(activity)
        listFiles.adapter = adapter

        arguments?.let { bundle ->
            val passedArguments = ClientFragmentArgs.fromBundle(bundle)
            clientViewModel.getClient(passedArguments.clientId)
                .observe(viewLifecycleOwner, Observer { client ->
                    name.text = client.name
                    order.text = client.order
                    terms.text = client.terms
                    clientId = client.id

                    val calendar = Calendar.getInstance()
                    val dateFormat = DateFormat.getDateFormat(view.context)
                    calendar.timeInMillis = client.date
                    date.text = dateFormat.format(calendar.time)

                    adapter.setFiles(requireContext().getFiles(clientId))

                    if (requireContext().proposalExists(clientId)) {
                        btnProposal.visibility = View.INVISIBLE
                    } else {
                        btnProposal.setOnClickListener {
                            requireContext().buildPdf(client)
                            it.visibility = View.INVISIBLE
                            adapter.setFiles(requireContext().getFiles(clientId))
                        }
                    }
                })
        }
    }

    override fun onClick(file: File) {
        val uri: Uri? = try {
            FileProvider.getUriForFile(
                requireContext(),
                "com.psdemo.globomanticssales.fileprovider",
                file
            )
        } catch (e: IllegalArgumentException) {
            Log.e("Client fragment", "the selected file can't be shared: $file")
            null
        }

        if (uri != null) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, activity!!.contentResolver.getType(uri))
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }
    }
}