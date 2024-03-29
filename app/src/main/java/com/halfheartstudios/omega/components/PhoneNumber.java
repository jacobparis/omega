package com.halfheartstudios.omega.components;

import android.graphics.ComposePathEffect;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Jacob on 2017-09-08.
 */

public class PhoneNumber extends Component {
    private String value;

    private HashSet<String> intents;

    public PhoneNumber() {
        super("PhoneNumber");
        intents = new HashSet<>();
        intents.add("Name");
        intents.add("Contact");
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Component setValue(String number) {
        // Parse individual characters
        char[] chars = number.toUpperCase().toCharArray();
        for(int i = 0; i < number.length(); i++) {
            char c = chars[i];
            // Remove non-conforming characters
            if(!Character.isLetterOrDigit(c)) {
                chars[i] = ' ';
            }

            // Convert letters to numbers T9 style
            if(Character.isLetter(c)) {
                switch(c) {
                    case 'A':case 'B':case 'C': chars[i] = '2'; break;
                    case 'D':case 'E':case 'F': chars[i] = '3'; break;
                    case 'G':case 'H':case 'I': chars[i] = '4'; break;
                    case 'J':case 'K':case 'L': chars[i] = '5'; break;
                    case 'M':case 'N':case 'O': chars[i] = '6'; break;
           case 'P':case 'Q':case 'R':case 'S': chars[i] = '7'; break;
                    case 'T':case 'U':case 'V': chars[i] = '8'; break;
           case 'W':case 'X':case 'Y':case 'Z': chars[i] = '9'; break;
                }
            }
        }

        this.value = String.valueOf(chars).replaceAll("[^0-9]", "");

        // Format US-Phone style
        if(this.value.length() == 11) { // 1-234-345-4567
            this.value = String.format("+%s (%s) %s-%s", this.value.substring(0, 1), this.value.substring(1, 4), this.value.substring(4, 7), this.value.substring(7, 11));
        } else if (this.value.length() == 10) { // 123-345-4567
            this.value = String.format("(%s) %s-%s", this.value.substring(0, 3), this.value.substring(3, 6), this.value.substring(6, 10));
        } else if (this.value.length() == 7) { // 123-3456
            this.value = String.format("%s-%s", this.value.substring(0, 3), this.value.substring(3, 7));
        }
        return this;
    }

    @Override
    public HashSet<String> getIntents() {
        return this.intents;
    }
}
