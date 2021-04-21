package tm.model.database;

import tm.model.tournament.Tournament;

public abstract class TournamentTypeMeta {
    private static final String swissSystemColor = "#bd1919";
    private static final String swissSystemSecondaryColor = "#db7070";
    private static final String swissSystemName = "Swiss system";
    private static final String swissSystemDescription = "Princíp Swiss System spočíva v rozdelení hráčov tak, aby proti sebe v každom kole hrali rovnako alebo aspoň čo najpodobnejšie kvalitný hráči z predchádzajúcich kôl. Zároveň platí že každý hráč hrá s konkrétnym iným hráčom najviac jeden zápas.";
    private static final String swissSystemParticipants = "Jednotlivci, tímy...";
    private static final String swissSystemSuitsFor = "Bežne užívaný typ rozpisu zápasov turnajov v šachu, bridžu, scrabble, squashi, bedmintone, pétanque, guličkách a ďalších hrách";
    private static final String swissSystemAlsoKnownAs = "Dutch system, Monrad system";

    private static final String roundRobinColor = "#168c22";
    private static final String roundRobinSecondaryColor = "#65e072";
    private static final String roundRobinName = "Round robin";
    private static final String roundRobinDescription = "Klasický režim pre takmer každý šport. Hráči alebo tímy sú rozdelené do skupín a každý hrá proti každému v tej istej skupine práve jeden zápas. Tento herný systém umožňuej aj hru na viac kôl respektíve tabuliek.";
    private static final String roundRobinParticipants = "Jednotlivci, tímy...";
    private static final String roundRobinSuitsFor = "Veľké turnaje, kvalifikáciu pred vyraďovacím kolom, náhodné rozdelenie do skupín...";
    private static final String roundRobinAlsoKnownAs = "All-play-all, Free for all";

    //TODO DOUBLE AND SINGLE ELIMINATION

}
