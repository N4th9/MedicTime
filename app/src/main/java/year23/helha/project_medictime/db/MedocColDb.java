package year23.helha.project_medictime.db;

public abstract class MedocColDb {
    public static final class MedocTable {
        public static final String NAME = "Medicaments";
        public static final class cols {
            public static final String TITLE = "TitreMedoc";
            public static final String DUREE = "DureeParDefaut";
            public static final String MATIN = "Matin";
            public static final String MIDI = "Midi";
            public static final String SOIR = "Soir";
        }
    }

    public static final class PriseTable {
        public static final String NAME = "MedocList";
        public static final class cols{
            public static final String MEDOC_ID="IdMedoc";
            public static final String DEBUT="DateDebut";
            public static final String FIN="DateFin";
            public static final String MATIN="Matin";
            public static final String MIDI="Midi";
            public static final String SOIR="Soir";
        }
    }
}