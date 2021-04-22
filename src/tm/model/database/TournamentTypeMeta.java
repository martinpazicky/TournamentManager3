package tm.model.database;

import tm.model.tournament.Tournament;

public abstract class TournamentTypeMeta {
    private static final String swissSystemColor = "#bd1919";
    private static final String swissSystemSecondaryColor = "#db7070";
    private static final String swissSystemName = "Swiss system";
    private static final String swissSystemDescription = "Princíp Swiss System spočíva v rozdelení hráčov tak, aby proti sebe v každom kole hrali rovnako alebo aspoň čo najpodobnejšie kvalitný hráči z predchádzajúcich kôl. Zároveň platí že každý hráč hrá s konkrétnym iným hráčom najviac jeden zápas.";
    private static final String swissSystemParticipants = "Jednotlivci, tímy...";
    private static final String swissSystemSuitsFor = "Bežne užívaný typ rozpisu zápasov turnajov v šachu, bridžu, scrabble, bedmintone a ďalších hrách";
    private static final String swissSystemAlsoKnownAs = "Dutch system, Monrad system";

    private static final String roundRobinColor = "#168c22";
    private static final String roundRobinSecondaryColor = "#65e072";
    private static final String roundRobinName = "Round robin";
    private static final String roundRobinDescription = "Klasický režim pre takmer každý šport. Hráči alebo tímy sú rozdelené do skupín a každý hrá proti každému v tej istej skupine práve jeden zápas. Tento herný systém umožňuej aj hru na viac kôl respektíve tabuliek.";
    private static final String roundRobinParticipants = "Jednotlivci, tímy...";
    private static final String roundRobinSuitsFor = "Veľké turnaje, kvalifikáciu pred vyraďovacím kolom, náhodné rozdelenie do skupín...";
    private static final String roundRobinAlsoKnownAs = "All-play-all, Free for all";

    private static final String singleEliminationColor = "#1958a6";
    private static final String singleEliminationSecondaryColor = "#60a0f0";
    private static final String singleEliminationName = "Single elimination";
    private static final String singleEliminationDescription = "Typ eliminačného turnaja, kde je porazený každý zápas okamžite vyradený z turnaja. Každý víťaz bude v ďalšom kole hrať ďalšie, až do finálového zápasu, ktorého víťazom sa stane majster turnaja.";
    private static final String singleEliminationParticipants = "Jednotlivci, tímy...";
    private static final String singleEliminationSuitsFor = "Play-off, poker, tímové aj individuálne športy.";
    private static final String singleEliminationAlsoKnownAs = "Quick death, Knock-out, Brackets";

    private static final String doubleEliminationColor = "#14826e";
    private static final String doubleEliminationSecondaryColor = "#55edd2";
    private static final String doubleEliminationName = "Double elimination";
    private static final String doubleEliminationDescription = "Dvojitý eliminačnej turnaj je súťaž s definovaným počtom účastníkov, kde tímy alebo hráči nie sú vyradení, alebo vylúčení, kým neprehrajú dve hry alebo zápasy. To sa líši od mnohých iných turnajov, kde jedna prehra eliminuje tím alebo hráča.";
    private static final String doubleEliminationParticipants = "Jednotlivci, tímy...";
    private static final String doubleEliminationSuitsFor = "Ping-pingové, tenisové turnaje, poker, tímové aj individuálne športy.";
    private static final String doubleEliminationAlsoKnownAs = "Double Knock-out, Double KO";


    public static String getSwissSystemColor() {
        return swissSystemColor;
    }

    public static String getSwissSystemSecondaryColor() {
        return swissSystemSecondaryColor;
    }

    public static String getSwissSystemName() {
        return swissSystemName;
    }

    public static String getSwissSystemDescription() {
        return swissSystemDescription;
    }

    public static String getSwissSystemParticipants() {
        return swissSystemParticipants;
    }

    public static String getSwissSystemSuitsFor() {
        return swissSystemSuitsFor;
    }

    public static String getSwissSystemAlsoKnownAs() {
        return swissSystemAlsoKnownAs;
    }

    public static String getRoundRobinColor() {
        return roundRobinColor;
    }

    public static String getRoundRobinSecondaryColor() {
        return roundRobinSecondaryColor;
    }

    public static String getRoundRobinName() {
        return roundRobinName;
    }

    public static String getRoundRobinDescription() {
        return roundRobinDescription;
    }

    public static String getRoundRobinParticipants() {
        return roundRobinParticipants;
    }

    public static String getRoundRobinSuitsFor() {
        return roundRobinSuitsFor;
    }

    public static String getRoundRobinAlsoKnownAs() {
        return roundRobinAlsoKnownAs;
    }

    public static String getSingleEliminationColor() {
        return singleEliminationColor;
    }

    public static String getSingleEliminationSecondaryColor() {
        return singleEliminationSecondaryColor;
    }

    public static String getSingleEliminationName() {
        return singleEliminationName;
    }

    public static String getSingleEliminationDescription() {
        return singleEliminationDescription;
    }

    public static String getSingleEliminationParticipants() {
        return singleEliminationParticipants;
    }

    public static String getSingleEliminationSuitsFor() {
        return singleEliminationSuitsFor;
    }

    public static String getSingleEliminationAlsoKnownAs() {
        return singleEliminationAlsoKnownAs;
    }

    public static String getDoubleEliminationColor() {
        return doubleEliminationColor;
    }

    public static String getDoubleEliminationSecondaryColor() {
        return doubleEliminationSecondaryColor;
    }

    public static String getDoubleEliminationName() {
        return doubleEliminationName;
    }

    public static String getDoubleEliminationDescription() {
        return doubleEliminationDescription;
    }

    public static String getDoubleEliminationParticipants() {
        return doubleEliminationParticipants;
    }

    public static String getDoubleEliminationSuitsFor() {
        return doubleEliminationSuitsFor;
    }

    public static String getDoubleEliminationAlsoKnownAs() {
        return doubleEliminationAlsoKnownAs;
    }
}
