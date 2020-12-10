package com.radhika.code.fidelity.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radhika.code.fidelity.data.model.SearchData
import com.radhika.code.fidelity.data.repository.MainRepository
import com.radhika.code.fidelity.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val users = MutableLiveData<Resource<SearchData>>()
    private val compositeDisposable = CompositeDisposable()

    fun fetchUsers(searchText: String) {
        compositeDisposable.add(
            mainRepository.getUsers(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {Resource.loading(null)}
                .subscribe({ userList ->
                    users.postValue(Resource.success(userList))
                }, {
                    users.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUsers(): LiveData<Resource<SearchData>>{
        return users
    }
}