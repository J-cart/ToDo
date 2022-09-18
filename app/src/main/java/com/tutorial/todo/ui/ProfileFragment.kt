package com.tutorial.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tutorial.todo.database.CategoryAppData
import com.tutorial.todo.databinding.FragmentProfileBinding
import com.tutorial.todo.model.epoxycontroller.ProfileController

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = ProfileController {
            val navigate = ProfileFragmentDirections.actionProfileFragmentToAddCategory()
            findNavController().navigate(navigate)
        }
        binding.recyclerView.setController(controller)
        activityViewModel.categoryData.observe(viewLifecycleOwner) {
            controller.categoryList = it  as ArrayList<CategoryAppData>
        }
        controller.deleteBump {
            activityViewModel.deleteAllCategory()
        }


        // I tried using the onClicked interface but it throws the error that MainViewModel appRepository hasn't been initialized
        //i'm not sure about why it does that but this method works ...more reading and practice to do
        EpoxyTouchHelper.initSwiping(binding.recyclerView)
            .right()
            .withTarget(ProfileController.ProfileEpoxyController::class.java)
            .andCallbacks(object :
                EpoxyTouchHelper.SwipeCallbacks<ProfileController.ProfileEpoxyController>() {
                override fun onSwipeCompleted(
                    model: ProfileController.ProfileEpoxyController?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val removedItem = model?.categoryAppData ?: return
                    activityViewModel.delete(removedItem)
                }
            })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}



