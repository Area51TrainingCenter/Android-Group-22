package pe.area51.parcelabletest;

import android.os.Parcel;
import android.os.Parcelable;

public class Visit implements Parcelable {

    private final long id;
    private final Person person;
    private final long creationTimestamp;

    public Visit(long id, Person person, long creationTimestamp) {
        this.id = id;
        this.person = person;
        this.creationTimestamp = creationTimestamp;
    }

    protected Visit(Parcel in) {
        id = in.readLong();
        person = in.readParcelable(Person.class.getClassLoader());
        creationTimestamp = in.readLong();
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(person, flags);
        dest.writeLong(creationTimestamp);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", person=" + person +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }
}
