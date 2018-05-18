package th.rmutsv.co.th.firebase;

import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference,databaseReference2,text;

    private static final String TAG = "LED Control";
    public Button Switch,Switch2;
    public Integer Value,Value_refer,Value2,Value_refer2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();

        /*------------------------------ LED1 ---------------------------------------*/
        databaseReference = firebaseDatabase.getReference("Switch1/LED1");

        Switch = (Button) findViewById(R.id.led1);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG,"Value is" + Value);

                if (Value == 1) {
                    Switch.setText("ON");
                    Value_refer = 0;
                }else {
                    Switch.setText("OFF");
                    Value_refer = 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.setValue(Value_refer);
            }
        });

        /*------------------------------ LED2 ---------------------------------------*/
        databaseReference2 = firebaseDatabase.getReference("Switch1/LED2");

        Switch2 = (Button) findViewById(R.id.led2);
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Value2 = dataSnapshot.getValue(Integer.class);
                Log.d(TAG,"Value is" + Value2);

                if (Value2 == 1) {
                    Switch2.setText("ON");
                    Value_refer2 = 0;
                }else {
                    Switch2.setText("OFF");
                    Value_refer2 = 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        Switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference2.setValue(Value_refer2);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        text = firebaseDatabase.getReference();
        textView = (TextView) findViewById(R.id.textView2);
        text.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                String index = String.valueOf(map.get("index"));
                textView.setText(index);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
