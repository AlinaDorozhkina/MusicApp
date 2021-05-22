package ru.alinadorozhkina.musicapp.mvp.presenter.list

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?

    fun bindView(view: V)
    fun getCount(): Int
}

interface ITopListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?

    fun bindView(view: V)
    fun getCount(): Int
    fun playClicked(position: Int,view: V)
    fun stopClicked()
    fun favouritesClicked(position: Int)

}