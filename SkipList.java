import java.util.ArrayList;
import java.util.Collections;

public class SkipList {

    public SkipListNode head;
    public SkipListNode tail;
    public int height;
    public Randomizer randomizer;
    private final int NEG_INF = Integer.MIN_VALUE; // -inf
    private final int POS_INF = Integer.MAX_VALUE; // +inf

    SkipList() {
        /*
         * DO NOT EDIT THIS FUNCTION
         */
        this.head = new SkipListNode(NEG_INF, 1);
        this.tail = new SkipListNode(POS_INF, 1);
        this.head.next.add(0, this.tail);
        this.tail.next.add(0, null);
        this.height = 1;
        this.randomizer = new Randomizer();
    }


    public SkipListNode locate(int target, int level) {
        SkipListNode curr = this.head;
        SkipListNode prev = null;
        int i = level ;
        while (curr.value <= target && curr!=null) {
            prev=curr;
            curr = curr.next.get(i);
        }
        return prev;
    }

    public SkipListNode weakLocate(int target, int level) {
        SkipListNode curr = this.head;
        SkipListNode prev = null;
        int i = level;
        while (curr.value <= target && curr!=null) {
            prev=curr;
            curr = curr.next.get(i);
        }
        return prev;
    }

    public SkipListNode skipSearch(int num) {
        SkipListNode a = null;
        a = locate(num,0);
        return a;
    }

    public void deleteHelper(int num){
        SkipListNode a = locate(num,0);
        int delTowerHeight = a.height;
        int i = 0;
        while(i<delTowerHeight){
            SkipListNode b = weakLocate(num,i);
            b.next.add(i,a.next.get(i));
            a.next.add(i,null);
            i++;
        }
    }

    public void canIncreaseLevel(int level) {
        if (level > this.height) {
            this.height++;
            this.head.height++;
            this.tail.height++;
            this.head.next.add(this.head.height - 1, this.tail);
            this.tail.next.add(this.tail.height - 1, null);
        }
    }

    public boolean delete(int num) {
        // TO be completed by students
        boolean check = search(num);
        if(check==true){
            deleteHelper(num);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean search(int num) {
        // TO be completed by students
        SkipListNode a = skipSearch(num);
        if (a.value == num) {
            return true;
        }
        return false;
    }

    public Integer upperBound(int num) {
        // TO be completed by students
        SkipListNode a = skipSearch(num);
        return a.next.get(0).value;
    }

    public void insert(int num) {
        // TO be completed by students
        SkipListNode ans = new SkipListNode(num, 1);
        do {
            ans.height++;
            canIncreaseLevel(ans.height);
        } while (randomizer.binaryRandomGen() == true);

        SkipListNode s = this.head;
        ArrayList<SkipListNode> arr = new ArrayList<>();
        ArrayList<SkipListNode> arrNext = new ArrayList<>();
        int i = this.height-1;
        while(i>=0){
            s=locate(num,i);
            arr.add(s);
            arrNext.add(s.next.get(i));
            i--;
        }

        Collections.reverse(arr);
        Collections.reverse(arrNext);

        i=0;
        while (i<ans.height){
            ans.next.add(i,arrNext.get(i));
            arr.get(i).next.add(i,ans);
            i++;
        }
    }

    public void print() {
        /*
         * DO NOT EDIT THIS FUNCTION
         */
        for (int i = this.height; i >= 1; --i) {
            SkipListNode it = this.head;
            while (it != null) {
                if (it.height >= i) {
                    System.out.print(it.value + "\t");
                } else {
                    System.out.print("\t");
                }
                it = it.next.get(0);
            }
            System.out.println("null");
        }
    }

//    public static void main(String[] args) {
//        SkipList sl = new SkipList();
//        sl.insert(3);
//        sl.insert(1);
//        sl.insert(5);
//        sl.insert(8);
//        sl.insert(4);
//        sl.insert(5);
//        sl.insert(4);
//        sl.insert(3);
//
//        sl.insert(2);
////       sl.insert(9);
////        sl.insert(3);
//        sl.print();
////        System.out.println(sl.upperBound(3));
////
//    }
}