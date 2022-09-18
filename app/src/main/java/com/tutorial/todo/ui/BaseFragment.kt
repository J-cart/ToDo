package com.tutorial.todo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tutorial.todo.database.AppDataBase
import com.tutorial.todo.model.arch.ActivityViewModel

abstract class BaseFragment:Fragment() {

    protected val mainActivity:MainActivity
        get() = (activity as MainActivity)

    protected val appDataBase:AppDataBase
        get() = AppDataBase.getDatabase(requireActivity())

    protected  val activityViewModel: ActivityViewModel by activityViewModels()


}