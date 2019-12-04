package pt.technic.apps.minesfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Massadas
 */

public class RecordTable implements Serializable {

    private transient final int MAX_CHAR = 10;
    
    private String name;
    private long score;
    private String clickname;       //리팩토링8
    private int clickPoints;        //리팩토링8
    
    private transient ArrayList<RecordTableListener> listeners;
    public RecordTable() {
        name = "Player";
        score = 9999999;
        clickname= "Player";
        clickPoints = 9999;
        listeners = new ArrayList<>();
    }
    public String getClickname() {
    	return clickname;
    }
    
    public int getClickPoints() {
    	return clickPoints;
    }
    
    
    public String getName() {
        return name.substring(0, Math.min(MAX_CHAR, name.length()));
    }

    public long getScore() {
        return score;
    }

    //클릭수 레코드 업데이트
    public void setClickRecord(String clickname,int clickPoints) {
    	if(clickPoints<this.clickPoints) {
    		this.clickname=clickname;
    		this.clickPoints=clickPoints;
    		 notifyRecordTableUpdated();
    	}
    }
    
    //타임 레코드 업데이트 
    public void setRecord(String name, long score) {
        if (score < this.score) {
            this.name = name;
            this.score = score;
            notifyRecordTableUpdated();
        }
    }

    public void addRecordTableListener(RecordTableListener list) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(list);
    }

    public void removeRecordTableListener(RecordTableListener list) {
        if (listeners != null) {
            listeners.remove(list);
        }
    }

    private void notifyRecordTableUpdated() {
        if (listeners != null) {
            for (RecordTableListener list : listeners) {
                list.recordUpdated(this);
            }
        }
    }
}
