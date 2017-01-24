package co.rishe.paranoidtest.resource;

import java.util.List;

import co.rishe.graphql.ResourceModel;

/**
 * Created by Bardia on 12/18/16.
 */
public class FilmQuery implements ResourceModel {
    public Film film;

    public static class Film {
        public static String __id__ = null;

        public String title;
        public String openingCrawl;
        public String director;
        public List<String> producers;
        public String created;
    }
}
