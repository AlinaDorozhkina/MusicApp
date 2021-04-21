package ru.alinadorozhkina.musicapp.mvp.presenter.list

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?

    fun bindView(view: V)
    fun getCount(): Int
   fun playClicked(position: Int)
}