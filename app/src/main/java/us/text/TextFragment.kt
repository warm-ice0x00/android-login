package us.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import us.text.databinding.FragmentTextBinding
import java.io.File
import java.io.IOException

class TextFragment : Fragment() {
    private var _binding: FragmentTextBinding? = null
    private val binding
        get() = _binding!!

    private val file by lazy { File(requireContext().filesDir, "text.txt") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            binding.text.setText(file.readText())
        } catch (_: IOException) {}
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_text_fragment_to_login_fragment)
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            file.writeText(binding.text.text.toString())
            Snackbar.make(requireView(), R.string.saved, Snackbar.LENGTH_SHORT).show()
        } catch (_: IOException) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
