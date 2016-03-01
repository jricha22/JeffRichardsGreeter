package edu.westga.jeffrichardsgreeter;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Tests for greeter
 *
 * Created by Jeff on 2/29/2016.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    private void addNameAndPushGreet(MainActivity activity, String name) {
        final EditText nameEditText = (EditText) activity.findViewById(R.id.greet_edit_text);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(name);

        Button greetButton = (Button) activity.findViewById(R.id.greet_button);

        TouchUtils.clickView(this, greetButton);
    }

    public void testGreet() {
        MainActivity activity = getActivity();
        addNameAndPushGreet(activity, "Jake");
        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
        String actualText = greetMessage.getText().toString();
        assertEquals("Hello, Jake!", actualText);
    }

    public void testReverseEnabled() {
        MainActivity activity = getActivity();
        Button reverseButton = (Button) activity.findViewById(R.id.reverse_button);
        assertFalse(reverseButton.isEnabled());
    }

    public void testReverseEnabledAfterGreet() {
        MainActivity activity = getActivity();
        addNameAndPushGreet(activity, "Fred");
        Button reverseButton = (Button) activity.findViewById(R.id.reverse_button);
        assertTrue(reverseButton.isEnabled());
    }

    public void testStringIsReversedAfterReversePressed() {
        MainActivity activity = getActivity();
        addNameAndPushGreet(activity, "Jake");
        Button reverseButton = (Button) activity.findViewById(R.id.reverse_button);
        TouchUtils.clickView(this, reverseButton);
        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
        String actualText = greetMessage.getText().toString();
        assertEquals("!ekaJ ,olleH", actualText);
    }
}