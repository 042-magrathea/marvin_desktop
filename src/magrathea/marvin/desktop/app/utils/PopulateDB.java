package magrathea.marvin.desktop.app.utils;

import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author boscalent
 */
public class PopulateDB {

    private enum MODEL {
        USERS, TOURNAMENTS, PRIZES, GAMES, HOSTS, SYSTEMS,
    }
    private StringBuilder query;

    private static final int NUSERS = 1000;
    private static final int NPRIZES = 200;
    private static final int NGAMES = 100;
    private static final int NHOSTS = 200;
    private static final int NSYSTEMS = 200;
    private static final int NTOURNAMENTS = 200;

    // Refers from this index to insert from table
    private static final int INDEX_USERS = 0;
    private static final int INDEX_PRIZES = 5;
    private static final int INDEX_GAMES = 0;
    private static final int INDEX_HOSTS = 0;
    private static final int INDEX_SYSTEMS = 0;
    private static final int INDEX_TOURNAMENTS = 0;

    private Random r;

    public PopulateDB() {
        r = new Random();
    }

    public static void main(String[] args) {
        PopulateDB app = new PopulateDB();

        app.header();

        //app.query(MODEL.USERS, NUSERS);
        app.query(MODEL.PRIZES, NPRIZES);
        //app.query(MODEL.GAMES, NGAMES);
        //app.query(MODEL.HOSTS, NHOSTS);
        //app.query(MODEL.SYSTEMS, NSYSTEMS);
        //app.query(MODEL.TOURNAMENTS, NTOURNAMENTS);     // compute last

        app.tail();
    }

    // 1er LEVEL
    private void header() {
        query = new StringBuilder("-- POPULATE Marvin DB Dummy Model\n"
                + "USE magrathea\n");
    }

    private void query(MODEL model, int quantity) {
        switch (model) {
            case USERS:
                queryUsers(quantity);
                break;
            case TOURNAMENTS:
                queryTournaments(quantity);
                break;
            case PRIZES:
                queryPrizes(quantity);
                break;
            case GAMES:
                queryGames(quantity);
                break;
            case HOSTS:
                queryHosts(quantity);
                break;
        }
    }

    private void tail() {
        System.out.println(query.toString());
    }

    ////////////////////////////////////////////////////////////////////////////
    // AUX. 2on LVL
    ////////////////////////////////////////////////////////////////////////////
    private void queryUsers(int quantity) {
        StringBuilder sb = new StringBuilder("\n-- USERS\n");
        sb.append("INSERT INTO `magrathea`.`USER`\n(`publicName`, `name`, "
                + "`phone`, `eMail`, `ads`, `privateDes`, `publicDes`, `administrator`, "
                + "`editor`, `language`, `datePassword`, `password`, `markedForDeletion`, "
                + "`memberSince`) VALUES\n");   // ClearRequestDate off

        for (int i = 0; i < quantity; i++) {
            sb.append(String.format("('%s', '%s', '%s', '%s','%s', '%s', '%s','%s'" /* ADM || ED */
                    + ",'%s', '%s','%s', '%s', '%s')",
                    getNickName(), getName(), getPhone(), getEmail(), getBolean(90),
                    getIpsum(3), getIpsum(2), getRoleUser(), getLanguage(),
                    getDate(2010, 2016), getPassword(), "0", getDate(2009, 2016)));
            if (i != quantity - 1) {
                sb.append(",\n");
            } else {
                sb.append(";\n");
            }
        }
        query.append(sb.toString());
    }

    private void queryPrizes(int quantity) {
        // prize
        int indexPrize = INDEX_PRIZES;
        
        StringBuilder sb = new StringBuilder("\n-- PRIZE - Discount\n");
        sb.append("INSERT INTO `magrathea`.`PRIZE` (`name`, `description`, `TEMPLATE_idTEMPLATE`) VALUES \n");
        // prize-Disccount
        StringBuilder sb2 = new StringBuilder("\n-- PRIZE_DISCOUNT\n");
        sb2.append("INSERT INTO `magrathea`.`PRIZE_DISCOUNT` (`PRIZE_idPRIZE`, `disc`) VALUES \n");
        
        for (int i = 0; i < quantity/2; i++) {  // Half is disccount
            int discount = randBetween(1,8);
            sb.append(String.format("('%s', '%s','%s')",
                    ("Descuento " + discount * 10 + "%"), getIpsum(),  discount ));
            if (i != quantity/2 - 1) {
                sb.append(",\n");
            } else {
                sb.append(";\n");
            }
            sb2.append(String.format("('%s', '%s')", indexPrize++, discount*10));
            if (i != quantity/2 - 1) {
                sb2.append(",\n");
            } else {
                sb2.append(";\n");
            }
        }
        query.append(sb.toString());
        query.append(sb2.toString());
        
    }

    private void queryGames(int quantity) {
        StringBuilder sb = new StringBuilder("\n-- USERS\n");
        sb.append("INSERT INTO `magrathea`.`GAME` (`name`, `description`, `image`) VALUES");   // ClearRequestDate off

        for (int i = 0; i < quantity; i++) {
            sb.append(String.format("('%s', '%s', '%s')",
                    getGameName(), getGameDescription(), getImageGameURL()));
            if (i != quantity - 1) {
                sb.append(",\n");
            } else {
                sb.append(";\n");
            }
        }
        query.append(sb.toString());
    }

    private void queryHosts(int quantity) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    private void queryTournaments(int quantity) {
        // TODO
        /*
        INSERT INTO `magrathea`.`TOURNAMENT` (`idTOURNAMENT`, `name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES (NULL, 'OPEN DOORS DAY 2006', 'Magic The Gathering 6ºEd', 'Duis aute irure dolor in reprehenderit in voluptate velit', '2006-03-15 00:00:00', '0', '0', '1', '0', '3', '2', '115');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2007', 'D&D 3ºED', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '2007-03-15 00:00:00', '0', '0', '1', '0', '2', '1', '116');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2008', 'Star Treck CCG', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '2008-03-15 00:00:00', '0', '0', '1', '0', '1', '2', '117');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2009', 'Super Deck', 'Duis aute irure dolor in reprehenderit in voluptate velit', '2009-03-15 00:00:00', '0', '0', '1', '0', '3', '1', '118');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2010', 'Tank Commander: The Eastern Front', 'Excepteur sint occaecat cupidatat non proident. ', '2010-03-15 00:00:00', '0', '0', '1', '0', '2', '2', '119');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2011', 'SATM: JdR', 'Ullamco laboris nisi ut aliquip ex ea commodo consequat. ', '2011-03-15 00:00:00', '0', '0', '1', '0', '1', '1', '120');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2012', 'Magic The Gathering 8ºEd', 'Duis aute irure dolor in reprehenderit in voluptate velit', '2012-03-15 00:00:00', '0', '0', '1', '0', '3', '2', '121');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2013', 'Vampire TCG', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '2013-03-15 00:00:00', '0', '0', '1', '0', '2', '1', '122');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2014', 'II WW: Wargame', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', '2014-03-15 00:00:00', '0', '0', '1', '0', '1', '2', '123');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2015', 'Settlers of Catan', 'Excepteur sint occaecat cupidatat non proident. ', '2015-03-15 00:00:00', '0', '0', '1', '0', '3', '1', '124');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2016', 'Death Mach Simcity', 'Duis aute irure dolor in reprehenderit in voluptate velit', '2016-03-15 00:00:00', '0', '0', '1', '0', '2', '2', '125');
INSERT INTO `magrathea`.`TOURNAMENT` (`name`, `publicDes`, `privateDes`, `date`, `open`, `started`, `finished`, `cancelled`, `SYSTEM_idSYSTEM`, `TOURNAMENT_HOST_idTournamentHost`, `GAME_idGAME`) VALUES ('OPEN DOORS DAY 2017', 'Dangon Age: Origin Jdr', 'Ullamco laboris nisi ut aliquip ex ea commodo consequat. ', '2017-03-15 00:00:00', '1', '0', '0', '0', '1', '1', '126');
*/
    }

    ////////////////////////////////////////////////////////////////////////////
    // AUX. 3r LVL
    ////////////////////////////////////////////////////////////////////////////
    private static String[] NICKNAMES_UPPER = {"Dent", "Arnau", "Villa", "Iván",
        "Magrathea", "Marvin", "Slartir", "Zaphod", "Maria", "Anna", "Eva", "Joana", "Elisabeth", "Leia"};

    private static String[] NICKNAMES = {"dent", "arnau", "villa", "iván",
        "magrathea", "marvin", "slartir", "zaphod", "maria", "anna", "eva", "joana", "elisabeth", "leia"};

    private static String[] DOMAIN = {"com", "es", "cat", "net",
        "org", "info", "edu", "tv"};

    private static String[] LOREM_IPSUM = {
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
        "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
        "Ut enim ad minim veniam, quis nostrud exercitation. ",
        "Ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
        "Duis aute irure dolor in reprehenderit in voluptate velit",
        "esse cillum dolore eu fugiat nulla pariatur. ",
        "Excepteur sint occaecat cupidatat non proident. ",
        "Sunt in culpa qui officia deserunt mollit anim id est laborum. "
    };

    private static String[] LANG = {"english", "catalan", "spanish"};

    private static String[] GAMES = {"Lord", "Sir", "Dragon", "Master", "Runner", "Thunder", "Rage",
        "Heroes", "Super", "Dungeon", "Rings", "Necks", "Talisman", "Quest", "Chronicles", "Vampire", "Viking", "Revenge",
        "Avatar", "Legend", "Space"};

    private static String[] GAMES_TYPE = {"Juego de cartas coleccionable", "Juego de cartas no coleccionable",
        "Juego de rol", "Juego de rol en vivo", "Juego de mesa", "Wargame"};

    private static String[] GAMES_IMAGE = {"",null,
        "http://www.sjgames.com/gurps/books/VampireTheMasquerade/img/cover_sm.jpg",
        "http://cdn.themis-media.com/media/global/images/library/deriv/676/676873.jpg",
        "http://www.sabledrake.com/lotr_rpg.jpg",
        "https://www.kallistra.co.uk/images/IMG_0302-copy.jpg",
        "http://www.tabletoptitans.com/img/reports/0047_04.jpg",
        "https://www.l5r.com/files/2015/09/evil-portents-logo.png",
        "http://www.allcsgaming.com/wp-content/uploads/2014/03/MTGlogo.jpg",
        "http://www.arkadian.vg/wp-content/uploads/2013/10/Pokemon-TCG.jpg",
        "http://3.bp.blogspot.com/_M7qnJE4I_pA/TL2DVT7HQ1I/AAAAAAAAAQQ/V_mkFAqA4H4/s1600/aquelarre_latentacion_300.jpg"
    };

    private String getNickName() {
        return NICKNAMES_UPPER[r.nextInt(NICKNAMES_UPPER.length)] + "_"
                + r.nextInt(1000) + "-" + r.nextInt(999);
    }

    private String getName() {
        return NICKNAMES_UPPER[r.nextInt(NICKNAMES_UPPER.length)] + " "
                + NICKNAMES_UPPER[r.nextInt(NICKNAMES_UPPER.length)] + " "
                + NICKNAMES_UPPER[r.nextInt(NICKNAMES_UPPER.length)] + r.nextInt(999) + 1;
    }

    private String getPhone() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(r.nextInt(9));
        }
        return sb.toString();
    }

    private String getEmail() {
        return NICKNAMES[r.nextInt(NICKNAMES.length)]
                + "_" + (r.nextInt(9999) + 1) + "@"
                + NICKNAMES[r.nextInt(NICKNAMES.length)] + "."
                + DOMAIN[r.nextInt(DOMAIN.length)];
    }

    private String getBolean(int probability) {
        return (r.nextInt(100) < probability) ? "1" : "0";
    }

    private String getIpsum(int lines) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < r.nextInt(lines); i++) {
            sb.append(LOREM_IPSUM[r.nextInt(LOREM_IPSUM.length)]);
        }
        return sb.toString();
    }
    
    private String getIpsum() {
        return LOREM_IPSUM[r.nextInt(LOREM_IPSUM.length)];
    }

    private String getRoleUser() {
        int adm = 0;
        int editor = 0;
        adm = (r.nextInt(100) < 10) ? 1 : 0;     // 5%
        if (adm != 1) {
            editor = (r.nextInt(100) < 10) ? 1 : 0; // 10%
        }
        return adm + "','" + editor;
    }

    private String getLanguage() {
        return LANG[r.nextInt(LANG.length)];
    }

    private String getPassword() {
        return NICKNAMES[r.nextInt(NICKNAMES.length)] + r.nextInt(9) + "_"
                + DOMAIN[r.nextInt(DOMAIN.length)];
    }

    private String getDate(int minYear, int maxYear) {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(minYear, maxYear);
        gc.set(GregorianCalendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        return (gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH));
    }

    private String getGameName() {
        StringBuilder sb = new StringBuilder();
        if (r.nextInt(2) == 0) {
            sb.append("The ");
        }
        sb.append(String.format("%s %s%s",
                GAMES[r.nextInt(GAMES.length)],
                r.nextInt(2) == 0 ? "& " : "",
                GAMES[r.nextInt(GAMES.length)]));
        return sb.toString();
    }

    private String getGameDescription() {
        return GAMES_TYPE[r.nextInt(GAMES_TYPE.length)];
    }

    private String getImageGameURL() {
        return GAMES_IMAGE[r.nextInt(GAMES_IMAGE.length)];
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
