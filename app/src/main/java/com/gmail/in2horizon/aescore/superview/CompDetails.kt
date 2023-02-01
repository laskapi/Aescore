package com.gmail.in2horizon.aescore.superview


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.in2horizon.aescore.R
import com.gmail.in2horizon.aescore.data.User
import com.gmail.in2horizon.aescore.ui.theme.AescoreTheme
import com.gmail.in2horizon.aescore.viewmodel.CompsViewModel
import com.gmail.in2horizon.aescore.views.MSpinner
import com.gmail.in2horizon.aescore.views.`super`.DeleteEntityDialog
import com.gmail.in2horizon.aescore.views.`super`.EditButton
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompDetails(compViewModel: CompsViewModel, back: () -> Unit) {


    val comp = compViewModel.selectedComp.collectAsState()
    val coroutine = rememberCoroutineScope()
    val users = compViewModel.users.collectAsState()

    var addedUsersToDelete = remember { mutableStateListOf<User>() }



    Column(
        modifier = Modifier.fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {

        //------name Row---------------------------------
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            var isEnabled by remember {
                mutableStateOf(false)
            }


            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                value = comp.value.name,
                onValueChange = { compViewModel.updateLocalSelectedComp(name = it) },
                label = { Text(text = stringResource(id = R.string.username)) },
                enabled = isEnabled
            )

            EditButton(
                modifier = Modifier.weight(0.3f),
            ) {
                isEnabled = true
            }
        }

        //--------admin Row-------------------------------
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            var isEnabled by remember {
                mutableStateOf(false)
            }

            Box(modifier = Modifier.weight(0.7f)) {

                MSpinner(
                    items = users.value, initial = comp.value.admin, onSelect = {
                        compViewModel.updateLocalSelectedComp(
                            admin = it as User
                        )
                    }, label = stringResource(id = R.string.admin), enabled = isEnabled
                )
            }
            EditButton(
                modifier = Modifier.weight(0.3f),
            ) {
                isEnabled = true
            }
        }

        //-------new user Row---------------------------
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            itemsIndexed(comp.value.users) { itemsIndex, it ->

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var isEnabled by remember {
                        mutableStateOf(false)
                    }
                    Box(modifier = Modifier.weight(0.7f)) {
                        val thisUser = it
            //            comp.value.users.forEach({Log.d("competix",it.toString()+"::"+thisUser
             //               .toString()+"::"+itemsIndex)})

                    //    val temp by mutableStateListOf()

                      Log.d("competixx",comp.value.toString())
                        Log.d("competixx1",users.value.minus(comp.value
                            .users).toString())

                        MSpinner(

                            items =users.value.minus(comp.value
                                .users),
                            initial = it,
                            onSelect = {
                                val index = comp.value.users.indexOf(thisUser)
                                compViewModel.setSelectedUsersUser(index,it as User)
                                Log.d("competitl", comp.value.users.count().toString())

                            },
                            label = stringResource(id = R.string.user) + " " + (itemsIndex + 1),
                            enabled =
                            isEnabled

                        )
                    }
                    EditButton(
                        modifier = Modifier.weight(0.3f),
                    ) {
                        isEnabled = true
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    FilledIconButton(onClick = { addedUsersToDelete.add(it) }) {
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = stringResource(id = R.string.delete)
                        )
                    }


                }


            }
        }

        AescoreTheme() {
            OutlinedButton(
                onClick = {
                        compViewModel.addSelectedCompUser(User())
                }) {
                Text(text = stringResource(id = R.string.add_new))
            }
        }
        if (addedUsersToDelete.isNotEmpty()) {
            DeleteEntityDialog(delete = {
                  compViewModel.removeSelectedCompUsers(addedUsersToDelete)
            }, confirmAuth =
            compViewModel::confirmAuthAsync,
                dismiss = { addedUsersToDelete.clear() })
        }

        //-----------cancel and save Row----------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = {
                back()
            }) {
                Text(text = stringResource(id = android.R.string.cancel))
            }

            OutlinedButton(onClick = {

                //  addRandomUserForTests(coroutine,usersViewModel::updateLocalSelectedUser)
                coroutine.launch {
                    val result = compViewModel.updateComp().await()
                    if (result) {
                        back()
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.save_and_exit))
            }

        }


    }
}