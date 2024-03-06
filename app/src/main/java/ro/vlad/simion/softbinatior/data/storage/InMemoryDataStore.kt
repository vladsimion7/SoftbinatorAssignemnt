package ro.vlad.simion.softbinatior.data.storage

object InMemoryDataStore : IDataStore {
    private lateinit var authorizationBearer: String

    override fun setAuthBearer(authBearer: String) {
        this.authorizationBearer = authBearer
    }

    override fun getAuthBearer() = authorizationBearer

    override fun hasAuthBearer() = ::authorizationBearer.isInitialized
}