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
    private String Clickname;
    private int ClickPoints;
    
    private transient ArrayList<RecordTableListener> listeners;
   // private transient ArrayList<RecordTableListener> Clicklist;
    public RecordTable() {
        name = "abc";
        score = 9999999;
        Clickname= "abc";
        ClickPoints = 9999;
        listeners = new ArrayList<>();
    }
    public String getClickname() {
    	return Clickname;
    }
    
    public int getClickPoints() {
    	return ClickPoints;
    }
    
    
    public String getName() {
        return name.substring(0, Math.min(MAX_CHAR, name.length()));
    }

    public long getScore() {
        return score;
    }

    //클릭수 레코드 업데이트
    public void setClickRecord(String Clickname,int ClickPoints) {
    	if(ClickPoints<this.ClickPoints) {
    		this.Clickname=Clickname;
    		this.ClickPoints=ClickPoints;
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

//    public void addRecordTableListener2(RecordTableListener click) {
//        if (Clicklist == null) {
//        	Clicklist = new ArrayList<>();
//        }
//        Clicklist.add(click);
//    }
//
//    public void removeRecordTableListener2(RecordTableListener click) {
//        if (Clicklist != null) {
//        	Clicklist.remove(click);
//        }
//    }
//    private void notifyRecordTableUpdated2() {
//        if (Clicklist != null) {
//            for (RecordTableListener click : Clicklist) {
//                click.recordUpdated(this);
//            }
//        }
//    }
    
    private void notifyRecordTableUpdated() {
        if (listeners != null) {
            for (RecordTableListener list : listeners) {
                list.recordUpdated(this);
            }
        }
    }
}
