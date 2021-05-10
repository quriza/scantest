package com.bignerdranch.android.scantest

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.ar2code.mutableliveevent.EventArgs
import ru.ar2code.mutableliveevent.MutableLiveEvent
//import ru.logsis.logsiswork._presentation.utils.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor() : ViewModel() {
    private val liveData = MutableLiveData<SharedItem>()
    private val singleLiveData = SingleLiveEvent<SharedItem>()
    private val actionLiveData = SingleLiveEvent<SharedAction>()
    val eventLiveData = MutableLiveEvent<SharedEvent>()
    val eventLiveAction = MutableLiveEvent<SharedEventAction>()
    private val actionPublish = PublishSubject.create<SharedAction>()
    private val actionUpdateUserPublish = BehaviorSubject.create<SharedAction>()

    fun send(item: SharedItem) {
        liveData.postValue(item)
    }

    fun sendSingle(item: SharedItem) {
        singleLiveData.postValue(item)
    }

    fun send(item: SharedAction) {
        actionLiveData.value = item
    }

    fun sendEventData(item: SharedItem) {
        eventLiveData.value = SharedEvent(item)
    }
    fun sendEventAction(item: SharedAction) {
        eventLiveAction.value = SharedEventAction(item)
    }

    fun publishAction(item: SharedAction) {
        actionPublish.onNext(item)
    }

    fun publishUpdateUser(item: SharedAction) {
        actionUpdateUserPublish.onNext(item)
    }

    fun getSharedData(): LiveData<SharedItem> {
        return liveData
    }

    fun getSingleSharedData(): LiveData<SharedItem> {
        return singleLiveData
    }

    fun getSharedActionData(): LiveData<SharedAction> {
        return actionLiveData
    }

    fun getSharedActionObservable(): Observable<SharedAction> {
        return actionPublish
    }

    fun getSharedUpdateUserObservable(): Observable<SharedAction> {
        return actionUpdateUserPublish
    }

    class SharedItem(val id: String, val bundle: Bundle)
    class SharedAction(val id: String)
    class SharedEvent(sharedItem: SharedItem) : EventArgs<SharedItem>(sharedItem)
    class SharedEventAction(sharedAction: SharedAction) : EventArgs<SharedAction>(sharedAction)
}