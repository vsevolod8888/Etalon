package com.etalon.shashin.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.etalon.shashin.Prefferences
import com.etalon.shashin.database.OurDatabase
import com.etalon.shashin.database.SportPit
import com.etalon.shashin.database.SportPitDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    val dao: SportPitDao? = OurDatabase.getInstance(application)?.sportpitDao()
    val preferences = Prefferences(application)

    val listAminokislotu: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listVitaminsAndMinerals: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listWaterAndDrinks: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listGainers: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listCreatinAndEnergetics: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listProteins: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listProteinsBatonchic: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listFatBurn: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())
    val listAcceccuares: MutableStateFlow<List<SportPit>> = MutableStateFlow(listOf())


    init {
        getAminokistotu()?.onEach {
            listAminokislotu.value = it      //  на каждое изм в БД приходит в он ич список
        }?.launchIn(viewModelScope)          // launchIn(viewModelScope)  пока вью модель живет будет подписка

        getVitaminsAndMinerals()?.onEach {
            listVitaminsAndMinerals.value = it
        }?.launchIn(viewModelScope)
        getWaterAndDrinks()?.onEach {
            listWaterAndDrinks.value = it
        }?.launchIn(viewModelScope)
        getGainers()?.onEach {
            listGainers.value = it
        }?.launchIn(viewModelScope)
        getCreatinAndEnergetics()?.onEach {
            listCreatinAndEnergetics.value = it
        }?.launchIn(viewModelScope)
        getProteins()?.onEach {
            listProteins.value = it
        }?.launchIn(viewModelScope)
        getProteinsBatonchic()?.onEach {
            listProteinsBatonchic.value = it
        }?.launchIn(viewModelScope)
        getFatBurn()?.onEach {
            listFatBurn.value = it
        }?.launchIn(viewModelScope)
        getAcceccuares()?.onEach {
            listAcceccuares.value = it
        }?.launchIn(viewModelScope)

    }

    fun updateAmount(amount: Double, itemName: String) {
        dao?.updateAmountByItemName(amount, itemName)
    }

    fun updatePrice(price: Double, itemName: String) {
        dao?.updatePriceByItemName(price, itemName)
    }

    fun deleteSportPitByName(name: String) {
        dao?.deleteSportpitByName(name)
    }

    fun getAminokistotu(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("Aminokistotu")
    }

    fun getVitaminsAndMinerals(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("VitaminsAndMinerals")
    }
    fun getWaterAndDrinks(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("WaterAndDrinks")
    }
    fun getGainers(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("Gainers")
    }

    fun getCreatinAndEnergetics(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("CreatinAndEnergetics")
    }
    fun getProteins(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("Proteins")
    }

    fun getProteinsBatonchic(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("ProteinsBatonchic")
    }

    fun getFatBurn(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("FatBurn")
    }
    fun getAcceccuares(): Flow<List<SportPit>>? {
        return dao?.getSportPitListByCategoryLiveData("Acceccuares")
    }


    fun insertSportPitVM(sportPit: SportPit) {
        dao?.insertSportPit(sportPit)
    }


    private val _choosendetailSportpit = MutableSharedFlow<SportPit>()
    val choosendetailSportpit: SharedFlow<SportPit>
        get() = _choosendetailSportpit


    fun onClickDetailSportPit(choosenSportpit: SportPit) {
        viewModelScope.launch {
            _choosendetailSportpit.emit(choosenSportpit)
        }

    }

    fun insertCategoryVM(category: String) {
        preferences.insertCategory(category)
    }

    fun getCategoryVM(): String {
        return preferences.getCategory()
    }
}