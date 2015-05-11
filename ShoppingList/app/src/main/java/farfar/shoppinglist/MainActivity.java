package farfar.shoppinglist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ArrayList<String> arrayListShoppingList;
    private ArrayAdapter<String> arrayAdapterShoppingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayListShoppingList = new ArrayList<String>();
        arrayAdapterShoppingList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListShoppingList);
        ListView listViewShoppingList = (ListView)findViewById(R.id.listViewShoppingList);
        listViewShoppingList.setAdapter(arrayAdapterShoppingList);

        registerForContextMenu(listViewShoppingList);
    }

    

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
