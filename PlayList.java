/** Represnts a list of musical tracks. The list has a maximum capacity (int),
 *  and an actual size (number of tracks in the list, an int). */
class PlayList {
    private Track[] tracks;  // Array of tracks (Track objects)   
    private int maxSize;     // Maximum number of tracks in the array
    private int size;        // Actual number of tracks in the array

    /** Constructs an empty play list with a maximum number of tracks. */ 
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        tracks = new Track[maxSize];
        size = 0;
    }

    /** Returns the maximum size of this play list. */ 
    public int getMaxSize() {
        return maxSize;
    }
    
    /** Returns the current number of tracks in this play list. */ 
    public int getSize() {
        return size;
    }

    /** Method to get a track by index */
    public Track getTrack(int index) {
        if (index >= 0 && index < size) {
            return tracks[index];
        } else {
            return null;
        }
    }
    
    /** Appends the given track to the end of this list. 
     *  If the list is full, does nothing and returns false.
     *  Otherwise, appends the track and returns true. */
    public boolean add(Track track) {
        //if the last cell in tracks is not null, there is no more room to add another song to the playlist
        if(tracks[maxSize-1] != null) {
            return false;
        }
        else {
            tracks[size] = track;
            size++;
            return true;            
        }
    }

    /** Returns the data of this list, as a string. Each track appears in a separate line. */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {
        StringBuilder myList = new StringBuilder();
        for(int i=0; i<size; i++) {
            myList.append("\n");
            myList.append(tracks[i].toString());
        }
        String playList = myList.toString();
        return playList;
    }

    /** Removes the last track from this list. If the list is empty, does nothing. */
     public void removeLast() {
        //if the list is not empty
        if(size > 0) {
            tracks[size-1] = null;
            size--;
        }
    }
    
    /** Returns the total duration (in seconds) of all the tracks in this list.*/
    public int totalDuration() {
        int dur = 0;
        for(int i=0; i<size; i++) {
            dur += tracks[i].getDuration();
        }
        return dur;
    }

    /** Returns the index of the track with the given title in this list.
     *  If such a track is not found, returns -1. */
    public int indexOf(String title) {
        for(int i=0; i < size; i++) {
            if((tracks[i].getTitle().toLowerCase()).equals(title.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /** Inserts the given track in index i of this list. For example, if the list is
     *  (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     *  If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     *  If i is negative or greater than the size of this list, or if the list
     *  is full, does nothing and returns false. Otherwise, inserts the track and
     *  returns true. */
    public boolean add(int i, Track track) {
        //if the list is not full, i is not negative and i is not greater than maxSize
        if((tracks[maxSize-1] == null) && (i >= 0) && (i <= size)) {
            //if i is the last available cell in tracks, add the track to the last cell
            if(i == size) {
                tracks[i] = track;
                size++;
                return true;
            }
            //if i is between 0 and size (not including size)
            else {
                shiftRight(i);
                tracks[i] = track;
                return true;
            }
        }
        return false;
    }

    //this function shifts all tracks in the tracks array one cell to the right, starting from the cell i
    //assuming that the tracks array isn't full
    private void shiftRight(int i) {
        for(int j=size; j>i; j--) {
            tracks[j] = tracks[j-1];
        }
        tracks[i] = null;
        size++;
    }
     
    /** Removes the track in the given index from this list.
     *  If the list is empty, or the given index is negative or too big for this list, 
     *  does nothing and returns -1. */
    public void remove(int i) {
        //if the list isn't empty and i is not negative and not bigger than or equal to size
        if((tracks[0] != null) && (i >= 0) && (i < size)) {
            for(int j=i; j < size-1; j++) {
                tracks[j] = tracks[j+1];
            }
            tracks[size-1] = null;  //so that we won't have the same song twice
            size--;
        }
    }

    /** Removes the first track that has the given title from this list.
     *  If such a track is not found, or the list is empty, or the given index
     *  is negative or too big for this list, does nothing. */
    public void remove(String title) {
        //check if the given title exists in the current playlist
        //if size=0, the playlist is empty and nothing will happen
        for(int i = 0; i < size; i++) {
            if((tracks[i].getTitle()).equals(title)) {
                tracks[i] = null;   //delete the specific song
                //if the title was the last song in the playlist, we have changed it to null and can finish the function
                if(i == size-1) {
                    size--;
                    break;
                }
                //if the title wasn't last
                else {
                    //shift the rest of the songs one cell to the left
                    for(int j=i; j < size-1; j++) {
                        tracks[j] = tracks[j+1];
                    }
                    tracks[size-1] = null;
                    size--;
                }
            }
        }    
    }

    /** Removes the first track from this list. If the list is empty, does nothing. */
    public void removeFirst() {
        //if the list is not empty
        if(size > 0) {
            tracks[0] = null;
            for(int i=0; i < size-1; i++) {
                tracks[i] = tracks[i+1];
            }
            tracks[size-1] = null;
            size--;
        }
    }
    
    /** Adds all the tracks in the other list to the end of this list. 
     *  If the total size of both lists is too large, does nothing. */
    //// An elegant and terribly inefficient implementation.
     public void add(PlayList other) {
        if((this.size + other.size) <= this.maxSize) {
            for(int i=0; i<other.size; i++) {
                tracks[this.size+i] = other.tracks[i];
            }
            size = this.size + other.size;
        }
    }

    /** Returns the index in this list of the track that has the shortest duration,
     *  starting the search in location start. For example, if the durations are 
     *  7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the 
     *  minimum value (5) when starting the search from index 2.  
     *  If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {
        if(start >=0 && start <=size) {
            int minIndex = start;
            int min = tracks[start].getDuration();
            for(int i=start+1; i < size; i++) {
                if(tracks[i].getDuration() < min) {
                    min = tracks[i].getDuration();
                    minIndex = i;
                }
            }
            return minIndex;
        }
        return -1;
    }

    /** Returns the title of the shortest track in this list. 
     *  If the list is empty, returns null. */
    public String titleOfShortestTrack() {
        return tracks[minIndex(0)].getTitle();
    }

    /** Sorts this list by increasing duration order: Tracks with shorter
     *  durations will appear first. The sort is done in-place. In other words,
     *  rather than returning a new, sorted playlist, the method sorts
     *  the list on which it was called (this list). */
    public void sortedInPlace() {
        // Uses the selection sort algorithm,  
        // calling the minIndex method in each iteration.
        //// replace this statement with your code
        for(int i=0; i<this.size; i++) {
            int minIn = minIndex(i);
            Track temp = tracks[i];
            tracks[i] = tracks[minIn];
            tracks[minIn] = temp;
        }
    }
}
