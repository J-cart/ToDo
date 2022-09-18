package com.tutorial.todo.ui

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tutorial.todo.Interface.OnClicked
import com.tutorial.todo.R
import com.tutorial.todo.database.AppData
import com.tutorial.todo.databinding.FragmentListBinding
import com.tutorial.todo.model.epoxycontroller.ListController


class ListFragment : BaseFragment(), OnClicked {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var controller = ListController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            val navigate = ListFragmentDirections.actionListFragmentToAddItem("")
            findNavController().navigate(navigate)
        }


        binding.recyclerView.setController(controller)
//        controller.isLoading = true
        activityViewModel.itemsWithCategory.observe(viewLifecycleOwner) { datalist ->
            controller.allData = datalist
//        activityViewModel.homeViewStateLiveData.observe(viewLifecycleOwner) { datalist ->
//            controller.homeViewState = datalist

            EpoxyTouchHelper.initSwiping(binding.recyclerView)
                .right()
                .withTarget(ListController.DataEpoxyModel::class.java)
                .andCallbacks(object :
                    EpoxyTouchHelper.SwipeCallbacks<ListController.DataEpoxyModel>() {
                    override fun onSwipeCompleted(
                        model: ListController.DataEpoxyModel?,
                        itemView: View?,
                        position: Int,
                        direction: Int
                    ) {
                        val removedItem = model?.data?.AppData ?: return
                        activityViewModel.delete(removedItem)
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_order, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.sortMenu  ->{
                BottomSheetDrawer().show(childFragmentManager, BottomSheetDrawer.TAG)
                true
            }
            R.id.deleteAll  ->{
                activityViewModel.deleteAllItems()
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun itemClicked(data: AppData) {
        val navigate2 = ListFragmentDirections.actionListFragmentToAddItem(data.id)
        findNavController().navigate(navigate2)
    }



    override fun onResume() {
        super.onResume()
//        (activity as MainActivity).hideKeyboard(requireView()) OR since the activity has been casted at globalScope(Basefragment)>>
        mainActivity.hideKeyboard(requireView())
    }
}