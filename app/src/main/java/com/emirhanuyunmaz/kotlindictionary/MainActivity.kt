package com.emirhanuyunmaz.kotlindictionary

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.room.Room
import com.emirhanuyunmaz.kotlindictionary.databinding.ActivityMainBinding
import com.emirhanuyunmaz.kotlindictionary.databinding.AlertDialogDictionaryBinding
import com.emirhanuyunmaz.kotlindictionary.room_database_dictionary.Dictionary
import com.emirhanuyunmaz.kotlindictionary.room_database_dictionary.DictionaryDao
import com.emirhanuyunmaz.kotlindictionary.room_database_dictionary.DictionaryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var job:Job
    private lateinit var jobMain:Job
    private lateinit var jobDelete:Job
    private lateinit var jobUpdete:Job
    private lateinit var db:DictionaryDatabase
    private lateinit var dictionaryDao: DictionaryDao
    private lateinit var adapter: DictionaryAdapter
    private lateinit var dictionaryArrayList: ArrayList<Dictionary>
    private lateinit var swipeController:SwipeController
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var alertView: AlertDialogDictionaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db=Room.databaseBuilder(this@MainActivity,DictionaryDatabase::class.java,"DICTIONARY").build()
        dictionaryDao=db.dictionaryDao()
        dictionaryArrayList= ArrayList()
        adapter= DictionaryAdapter(dictionaryArrayList)

        binding.recyclerViewDictionary.layoutManager=LinearLayoutManager(this@MainActivity)
        binding.recyclerViewDictionary.adapter=adapter

        jobMain= CoroutineScope(Dispatchers.IO).launch {
            dictionaryArrayList= ArrayList(dictionaryDao.getAll())
            withContext(Dispatchers.Main){
                binding.recyclerViewDictionary.layoutManager=LinearLayoutManager(this@MainActivity)
                adapter= DictionaryAdapter(dictionaryArrayList)
                binding.recyclerViewDictionary.adapter=adapter
            }
        }

        swipeController=object :SwipeController(this@MainActivity){

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){
                    ItemTouchHelper.RIGHT->{
                        //Upload
                        alertDialogBuilder(this@MainActivity)
                        var adapterId=viewHolder.adapterPosition
                        var dictionayId=dictionaryArrayList[adapterId].id
                        alertView.editTextTurkish.setText(dictionaryArrayList[adapterId].turkishWord.toString())
                        alertView.editTextEnglish.setText(dictionaryArrayList[adapterId].englishWord.toString())
                        jobUpdete= CoroutineScope(Dispatchers.IO).launch {

                            var getSearchDictionary=dictionaryDao.getSearc(dictionayId)
                            getSearchDictionary.turkishWord=alertView.editTextTurkish.text.toString()
                            getSearchDictionary.englishWord=alertView.editTextEnglish.text.toString()
                            dictionaryDao.update(getSearchDictionary)

                            dictionaryArrayList= ArrayList(dictionaryDao.getAll())
                            withContext(Dispatchers.Main){
                                binding.recyclerViewDictionary.layoutManager=LinearLayoutManager(this@MainActivity)
                                adapter= DictionaryAdapter(dictionaryArrayList)
                                binding.recyclerViewDictionary.adapter=adapter
                            }


                        }

                        Toast.makeText(this@MainActivity, "Update", Toast.LENGTH_SHORT).show()
                    }

                    ItemTouchHelper.LEFT->{
                        //Delete
                        jobDelete=CoroutineScope(Dispatchers.IO).launch {

                            var adapterId=viewHolder.adapterPosition
                            var dictionayId=dictionaryArrayList[adapterId].id
                            var getSearchDictionary=dictionaryDao.getSearc(dictionayId)
                            dictionaryDao.delete(getSearchDictionary)
                            dictionaryArrayList= ArrayList(dictionaryDao.getAll())
                            withContext(Dispatchers.Main){
                                binding.recyclerViewDictionary.layoutManager=LinearLayoutManager(this@MainActivity)
                                adapter= DictionaryAdapter(dictionaryArrayList)
                                binding.recyclerViewDictionary.adapter=adapter
                            }

                        }
                        Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
                    }


                }

                super.onSwiped(viewHolder, direction)
            }

        }
        itemTouchHelper= ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewDictionary)

    }

    fun alertDialogBuilder(context: Context){

        val build=AlertDialog.Builder(context)
        alertView=AlertDialogDictionaryBinding.inflate(layoutInflater)
        build.setView(alertView.root)

        val dialog=build.setTitle("Enter Words").setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
            job= CoroutineScope(Dispatchers.IO).launch {
                var getTurkishWord=alertView.editTextTurkish.text.toString()
                var getEnglishWord=alertView.editTextEnglish.text.toString()

                var dictionaryData=Dictionary(getTurkishWord,getEnglishWord)
                dictionaryDao.insert(dictionaryData)
                dictionaryArrayList= ArrayList(dictionaryDao.getAll())
                withContext(Dispatchers.Main){
                    binding.recyclerViewDictionary.layoutManager=LinearLayoutManager(this@MainActivity)
                    adapter= DictionaryAdapter(dictionaryArrayList)
                    binding.recyclerViewDictionary.adapter=adapter
                }
            }


        }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
        }).create()
        dialog.show()

    }

    fun addWord_OnClick(view : View){
        alertDialogBuilder(this@MainActivity)
    }
}
