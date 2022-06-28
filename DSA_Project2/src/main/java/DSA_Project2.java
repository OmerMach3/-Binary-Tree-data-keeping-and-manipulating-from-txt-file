
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.InterruptedException;
import java.util.ArrayList;

public class DSA_Project2 {

    public static void main(String[] args) {
        SongBst bstById = new SongBst();
        SongBst bstByArtist = new SongBst();
        SongBst bstByNameKeys = new SongBst();
        ArrayList<Song> array = new ArrayList<>();
        int count = 0;
        System.out.println("Welcome to mp3 player your audios are being loaded...");
        try{
           Thread.sleep(2000);
       }
       catch(InterruptedException ex){
           ex.printStackTrace();
       }
        File file = new File("Songs.txt");
        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                String[] lineWords = line.split(";");
                // bstById.addNodeByID(lineWords[0], lineWords[1], lineWords[2], lineWords[3], lineWords[4]);

                array.add(new Song(lineWords[0], lineWords[1], lineWords[2], lineWords[3], lineWords[4], count));

                //System.out.println(count);
                count++;

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /*for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
            
        }*/
        for (int i = 0; i < array.size(); i++) {
            bstById.addSongIndexByID(array.get(i));

        }
       // bstById.traverseInOrder(bstById.root_index, array);
         for (int i = 0; i < array.size(); i++) {
            bstByArtist.addSongIndexByArtist("Rammstein",array.get(i));

        }
        bstByArtist.traverseInOrderByArtistByDataEfficency(bstByArtist.root_index, array,"Anonim") ;
        // bst.traverseInOrder(bst.root);
        //bstById.traverseInOrder(bstById.root_index);
    }
}

class Song {

    //Media media;
    // MediaPlayer player = new MediaPlayer(media);
    int index;
    String ID;
    
    String artist;
    String name;
    String genre;
    String year;
    Song right = null;
    Song left = null;

    public Song(String name, String artist, String ID, String genre, String year, int index) {
        this.index = index;
        this.artist = artist;
        this.name = name;
        this.ID = ID;
        this.genre = genre;
        this.year = year;
        
    }

    @Override
    public String toString() {
        return "artist: " + artist + " name: " + name + " ID: " + ID + " genre: " + genre + " year: " + year + " index:" + index;

    }

}

class SongBst {

    Song root;
    MyInteger root_index;
    
    public SongBst() {
        this.root = null;
        this.root_index = null;

    }
 public void traverseInOrderByDataEfficency(MyInteger focus, ArrayList<Song> list) {
       traverseInOrder(focus, list);
       for(int i = 0;i<list.size();i++){
           this.deleteSong(root, Integer.parseInt(list.get(i).ID));
       }
    }
 public void traverseInOrderByArtistByDataEfficency(MyInteger focus, ArrayList<Song> list,String artist) {
       traverseInArtistOrder(focus, list,artist);
       for(int i = 0;i<list.size();i++){
           this.deleteSong(root, Integer.parseInt(list.get(i).ID));
       }
    }
  public void traverseInArtistOrder(MyInteger focus, ArrayList<Song> list,String artist) {
        if (focus.left != null) {
            // System.out.println("traverseInOrder first if condition visited");
            traverseInOrder(focus.left, list);
        }
        if(artist.equals(list.get(focus.data).artist))
        System.out.println(list.get(focus.data));
        if (focus.right != null) {
            // System.out.println("traverseInOrder second if condition visited");
            traverseInOrder(focus.right, list);
        }
    }
    public void addSongIndexByID(Song song) {

        if (root == null) {
            this.root_index = new MyInteger(song.index);
            this.root = song;
        } else {
            Song tmp = root;
            Song parent = root;
            MyInteger parent_index = root_index;
            MyInteger tmp_index = root_index;
            while (tmp != null) {
                parent = tmp;
                parent_index = tmp_index;
                if (Integer.parseInt(song.ID) < Integer.parseInt(tmp.ID)) {
                    tmp_index = tmp_index.left;
                    tmp = tmp.left;

                } else if (Integer.parseInt(song.ID) > Integer.parseInt(tmp.ID)) {
                    tmp_index = tmp_index.right;
                    tmp = tmp.right;
                } else {
                    parent.ID = song.ID;
                    break;
                }
            }
            MyInteger n = new MyInteger(song.index);
            if (Integer.parseInt(song.ID) < Integer.parseInt(parent.ID)) {
                parent.left = song;
                parent_index.left = n;
            } else if (Integer.parseInt(song.ID) > Integer.parseInt(parent.ID)) {
                parent.right = song;
                parent_index.right = n;
            }

        }
        
    }
    public void addSongIndexByArtist(String artist,Song song){
         if (root == null&&artist.equals(song.artist)) {
            this.root_index = new MyInteger(song.index);
            this.root = song;
        } else {
            Song tmp = root;
            Song parent = root;
            MyInteger parent_index = root_index;
            MyInteger tmp_index = root_index;
            while (tmp != null&&tmp_index!=null) {
                parent = tmp;
                parent_index = tmp_index;
                if (Integer.parseInt(song.ID) < Integer.parseInt(tmp.ID)) {
                    tmp_index = tmp_index.left;
                    tmp = tmp.left;

                } else if (Integer.parseInt(song.ID) > Integer.parseInt(tmp.ID)) {
                    tmp_index = tmp_index.right;
                    tmp = tmp.right;
                } else {
                    parent.ID = song.ID;
                    break;
                }
            }
            MyInteger n = new MyInteger(song.index);
            if (Integer.parseInt(song.ID) < Integer.parseInt(parent.ID)&& artist.equals(song.artist)) {
                parent.left = song;
                parent_index.left = n;
            } else if (Integer.parseInt(song.ID) > Integer.parseInt(parent.ID)&& artist.equals(song.artist)) {
                parent.right = song;
                parent_index.right = n;
            }

        }
        
     }
    
   
    public void addSong(Song song) {
        
        if (root == null) {
            //this.root_index = new MyInteger(song.index);
            this.root = song;
        } else {
            Song tmp = root;
            Song parent = root;
            while (tmp != null) {
                parent = tmp;
                if (Integer.parseInt(song.ID) < Integer.parseInt(tmp.ID)) {
                    tmp = tmp.left;
                } else if (Integer.parseInt(song.ID) > Integer.parseInt(tmp.ID)) {
                    tmp = tmp.right;
                } else {
                    parent.ID = song.ID;
                    break;
                }
            }
            // MyInteger n = new MyInteger(song.index);
            if (Integer.parseInt(song.ID) < Integer.parseInt(parent.ID)) {
                parent.left = song;
            } else if (Integer.parseInt(song.ID) > Integer.parseInt(parent.ID)) {
                parent.right = song;
            }

        }

    }

    public void searchByName(String name, Song focus) {
        if (focus.left != null) {
            searchByArtist(name, focus.left);
        }
        if (name.equals(focus.name)) {
            System.out.println(focus);
            return;
        }

        if (focus.right != null) {
            searchByName(name, focus.right);
        }
    }

    public Song searchByID(String ID, Song focus) {
        if (focus == null) {
            return null;
        }
        if (focus.ID.equals(ID)) {
            return focus;
        } else if (Integer.parseInt(ID) < Integer.parseInt(focus.ID)) {
            return searchByID(ID, focus.left);
        } else {
            return searchByID(ID, focus.right);
        }

    }

    public void searchByArtist(String artist, Song focus) {
        if (focus.left != null) {
            searchByArtist(artist, focus.left);
        }
        if (artist.equals(focus.artist)) {
            System.out.println(focus);

        }

        if (focus.right != null) {
            searchByArtist(artist, focus.right);
        }
    }

    public void searchByGenre(String genre, Song focus) {
        if (focus.left != null) {
            searchByGenre(genre, focus.left);
        }
        if (genre.equals(focus.genre)) {
            System.out.println(focus);
        }

        if (focus.right != null) {
            searchByGenre(genre, focus.right);
        }
    }

    public void traverseInOrder(MyInteger focus, ArrayList list) {
        if (focus.left != null) {
            // System.out.println("traverseInOrder first if condition visited");
            traverseInOrder(focus.left, list);
        }
        System.out.println(list.get(focus.data));
        if (focus.right != null) {
            // System.out.println("traverseInOrder second if condition visited");
            traverseInOrder(focus.right, list);
        }
    }

    /* public void traverseInOrder(Song focus) {
        if (focus.left != null) {
            traverseInOrder(focus.left);
        }
        System.out.println(focus.index);
        if (focus.right != null) {
            traverseInOrder(focus.right);
        }
    }*/
    public void traverseWithLimits(Song focus, int upper, int lower) {
        if (focus.left != null) {
            traverseWithLimits(focus.left, upper, lower);
        }
        if (Integer.parseInt(focus.ID) <= upper && Integer.parseInt(focus.ID) >= lower) {
            System.out.println(focus);
        }

        if (focus.right != null) {
            traverseWithLimits(focus.right, upper, lower);
        }
    }

    public Song deleteSong(Song focus, int ID) {
        if (focus == null) {
            return null;
        }
        if (ID < Integer.parseInt(focus.ID)) {
            focus.left = deleteSong(focus.left, ID);
        } else if (ID > Integer.parseInt(focus.ID)) {
            focus.right = deleteSong(focus.right, ID);
        } else {
            if (focus.right == null) {
                return focus.left;
            }
            if (focus.left == null) {
                return focus.right;
            }
            Song t = focus;
            focus = min(t.right);
            focus.right = deleteMin(t.right);
            focus.left = t.left;
        }
        return focus;
    }

    public Song min(Song x) {
        if (root == null) {
            throw new NoSuchElementException("BST is empty!");
        }
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }

    public Song deleteMin(Song x) {
        if (root == null) {
            throw new NoSuchElementException("BST is empty!");
        }
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        return x;
    }
    public int size(Song n) {
        if (n == null) {
            return 0;
        } else {
            int a = size(n.left) + 1;
            int b = size(n.right);
            return (a + b);
        }
    }

    class MyInteger {

        int data;
        MyInteger left;
        MyInteger right;

        public MyInteger(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return Integer.toString(data);
        }

    }
}
