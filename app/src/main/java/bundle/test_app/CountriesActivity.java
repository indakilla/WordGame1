package bundle.test_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class CountriesActivity extends AppCompatActivity {

    //before app starts make an Map of words (dictionary)
    private HashMap<String, String> dictionary;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fourDefns;
    private int correct1;
    private int wrong1;
    private String CorrectScore;
    private String WrongScore;
    private String AfterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if
        setContentView(R.layout.activity_countries);
        AfterText = getString(R.string.city_country);
        correct1=0;
        wrong1=0;
        dictionary = new HashMap<String, String>();
        list = new ArrayList<String>();

        fourDefns = new ArrayList<String>();

        readWordsFromTxt();
        pickRandomWords();

    }

    public void readWordsFromTxt() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.capitals));

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String [] parts = line.split("\t");
            if (parts.length >= 2) {
                String word = parts[0];
                String defn = parts[1];
                list.add(word);
                dictionary.put(word,defn);
            }
        }
    }

    public void update1Score(){
        CorrectScore = getResources().getString(R.string.Correct, correct1);
        WrongScore = getResources().getString(R.string.Wrong, wrong1);
    }

    public void pickRandomWords() {

        TextView one = (TextView) findViewById(R.id.right);
        TextView two = (TextView) findViewById(R.id.wrong);
        update1Score();
        one.setText(CorrectScore);
        two.setText(WrongScore);

        ArrayList<String> fourWords = new ArrayList<String>();
        Collections.shuffle(list);
        for(int i=0; i<4;i++) {
            fourWords.add(list.get(i));
        }
        //now choose the first word. Always @final@ if its inside ClickListeners or something like that
        final String theWord = fourWords.get(0);
        //add question above
        TextView theWordView = (TextView) findViewById(R.id.Country);
        theWordView.setText(theWord+" "+AfterText);

        //clear if it had a data
        fourDefns.clear();
        for(String word : fourWords) {
            fourDefns.add(dictionary.get(word));
        }
        Collections.shuffle(fourDefns);

        //dumb adapter for the Listview to show this elements instead of strings.xml
        //++if adapter exist than do not initialize it again

        if (adapter == null) {
            adapter =
                    new ArrayAdapter<String>(/*insert activity, layout, array */this,
                            android.R.layout.simple_list_item_1,
                        /* changed LIST into fourDefns*/ fourDefns);
        } else {
            adapter.notifyDataSetChanged();
        }

        ListView listview = (ListView) findViewById(R.id.word_list);
        listview.setAdapter(adapter);
        // adding click event
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if they clicked
                String clickedItem = fourDefns.get(position);
                //if correct then
                String correctAnswer = dictionary.get(theWord);
                if (clickedItem.equals(correctAnswer))
                {
                    Toast.makeText(CountriesActivity.this, R.string.Well_done, Toast.LENGTH_SHORT).show();
                    correct1++;
                } else {
                    Toast.makeText(CountriesActivity.this, R.string.Bad_done, Toast.LENGTH_SHORT).show();
                    wrong1++;
                }
                pickRandomWords();
            }
        });

    }


    public void Menu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void CountryGuess(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}