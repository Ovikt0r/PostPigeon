package repository;

import java.util.ArrayList;
import java.util.Iterator;

public class TableArrayList<Recipient> extends ArrayList<Recipient> {
    public String toString() {
        Iterator<Recipient> it = iterator();
        if (! it.hasNext())
            return "The table is empty";

        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (;;) {
            Recipient recipient = it.next();
            sb.append(recipient == this ? "(this Collection)" : recipient);
            if (! it.hasNext())
                return sb.append(' ').toString();
        }
    }
}
