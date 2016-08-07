package com.devtau.popularmoviess2.model;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.widget.ImageView;
import com.devtau.popularmoviess2.utility.Constants;
import com.devtau.popularmoviess2.utility.Logger;
import com.devtau.popularmoviess2.utility.Utility;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static com.devtau.popularmoviess2.database.MoviesTable.*;
import android.databinding.BindingAdapter;

public class Movie {
    private final String LOG_TAG = Movie.class.getSimpleName();
    private long id;
    private String title;
    private String posterPath;
    private String plotSynopsis;
    private double userRating;
    private double popularity;
    private Calendar releaseDate = new GregorianCalendar(1970, 0, 1);


    public Movie(long id, String title, String posterPath, String plotSynopsis,
                 double userRating, double popularity, Calendar releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    public Movie(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
        title = cursor.getString(cursor.getColumnIndex(TITLE));
        posterPath = cursor.getString(cursor.getColumnIndex(POSTER_PATH));
        plotSynopsis = cursor.getString(cursor.getColumnIndex(PLOT_SYNOPSIS));
        userRating = cursor.getDouble(cursor.getColumnIndex(USER_RATING));
        try {
            releaseDate = new GregorianCalendar(1970, 0, 1);
            String dateString = cursor.getString(cursor.getColumnIndex(RELEASE_DATE));
            releaseDate.setTime(Utility.dateFormat.parse(dateString));
        } catch (ParseException e) {
            Logger.e(LOG_TAG, "while parsing releaseDate from Cursor", e);
            e.printStackTrace();
        }
    }


    //getters
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public double getPopularity() {
        return popularity;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear() {
        Calendar defaultDate = new GregorianCalendar(1970, 0, 1);
        if(getReleaseDate().compareTo(defaultDate) == 0) {
            return "---";
        } else {
            return String.valueOf(getReleaseDate().get(Calendar.YEAR));
        }
    }

    public void updateFields(Movie movie) {
        if(movie != null) {
            int fieldsUpdated = 0;
            if(!"".equals(movie.getTitle())) {
                title = movie.getTitle();
                fieldsUpdated++;
            }
            if(!"".equals(movie.getPosterPath())) {
                posterPath = movie.getPosterPath();
                fieldsUpdated++;
            }
            if(!"".equals(movie.getPlotSynopsis())) {
                plotSynopsis = movie.getPlotSynopsis();
                fieldsUpdated++;
            }
            if(0 != (movie.getUserRating())) {
                userRating = movie.getUserRating();
                fieldsUpdated++;
            }
            if(0 != (movie.getPopularity())) {
                popularity = movie.getPopularity();
                fieldsUpdated++;
            }
            if(null != (movie.getReleaseDate())) {
                releaseDate = movie.getReleaseDate();
                fieldsUpdated++;
            }
            Logger.v(LOG_TAG, "updateFields() finished. fieldsUpdated: " + String.valueOf(fieldsUpdated));
        } else {
            Logger.e(LOG_TAG, "updateFields() cannot receive null movie");
        }
    }

    public String getFormattedUserRating() {
        return String.valueOf(userRating) + '/' + 10;
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView view, String url) {
        Utility.loadImageToView(view.getContext(), url, view,
                Constants.DEFAULT_POSTER_WIDTH, Constants.DEFAULT_POSTER_HEIGHT);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass() || id == -1) return false;
        Movie that = (Movie) obj;
        if (that.id == -1) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id != -1 ? 31 * id : 0);
    }
}
