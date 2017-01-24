import java.util.List;

import co.rishe.graphql.ResourceModel;

public class MyQuery implements ResourceModel {
    public AllFilms allFilms;

    public static class AllFilms {
        public static int __first__ = 10;

        public List<Film> films = null;

        static public class Film {
            public String id;
            public String title;
            public String director;
            public int episodeID;
            public String releaseDate;
            public SpeciesConnection speciesConnection;
            static public class SpeciesConnection {
                public static int __first__ = 2;
                public List<Specie> species;

                public static class Specie {
                    public String id;
                }
            }
        }
    }
}