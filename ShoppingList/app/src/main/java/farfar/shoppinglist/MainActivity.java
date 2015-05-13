package farfar.shoppinglist;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> arrayListShoppingList;
    private ArrayAdapter<String> arrayAdapterShoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Here we have our listedview widget
        arrayListShoppingList = new ArrayList<String>();
        arrayAdapterShoppingList = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arrayListShoppingList);

        ListView listViewShoppingList = (ListView)findViewById(R.id.listViewShoppingList);
        listViewShoppingList.setAdapter(arrayAdapterShoppingList);

        //This will archive contextmenu, a contextmenu is a pop up meny which appears when an item
        // in the list is pressed long enough.
        registerForContextMenu(listViewShoppingList);

        try{
            Log.i("When create", "Hello, the on create has occured!");

            Scanner scan = new Scanner (openFileInput("ShoppingList.txt"));

            while(scan.hasNextLine()){
                String ShoppingList = scan.nextLine();
                arrayAdapterShoppingList.add(ShoppingList);
            }
            scan.close();
        }catch (Exception e){
            Log.i("When create", e.getMessage());
        }
    }

    //In this method we costimize what menu options we would like the user to choose from when the
    // pop up menu is shown. In our case we either want to delete the item or just cancel and return

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId() != R.id.listViewShoppingList){
            return;
        }

        menu.setHeaderTitle("What would you like to do??");
        String[] options = {"Delete Item", "Return"};

        for(String option : options){
            menu.add(option);
        }

    }
    //In this method we preforme what the user chose from the pop up meny, if he/she wanted to
    //delete then we remove the item from the list otherwise we leave it as it is. We also get the
    //index of the selected item so we can remove it from the arraylist
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedIndex = info.position;

        if(item.getTitle().equals("Delete Item")){
            arrayListShoppingList.remove(selectedIndex);
            arrayAdapterShoppingList.notifyDataSetChanged();
        }

        return true;
    }

    //Here we save and load the file so we could save the information. The save happens every time
    //we press the back button from the main activity.
    @Override
    public void onBackPressed(){
        try{
            Log.i("On back Pressed", "Hello, the on back pressed button has occoured.");
            PrintWriter SH = new PrintWriter (openFileOutput("ShoppingList.txt", Context.MODE_PRIVATE));

            for(String ShoppingList : arrayListShoppingList){
               SH.println(ShoppingList);
            }

            SH.close();
        }catch(Exception e){
            Log.i("On back pressed", e.getMessage());
        }

    }
    //When the button is pressed we add the text in the textfeild to the viewlist, if the textfield
    //is empty then we dont do a thing. After that the button is clicked we clear the textfeild.
    public void buttonAddClick(View v){
        EditText editTextShoppingList = (EditText)findViewById(R.id.editTextShoppingList);
        String ShoppingList = editTextShoppingList.getText().toString().trim();

        if(ShoppingList.isEmpty()){
            return;
        }

        arrayAdapterShoppingList.add(ShoppingList);
        editTextShoppingList.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
