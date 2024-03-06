package ro.vlad.simion.softbinatior.data.storage

interface IDataStore {
    fun setAuthBearer(authBearer:String)
    fun getAuthBearer():String
    fun hasAuthBearer():Boolean
}