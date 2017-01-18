package magrathea.marvin.desktop.tournament.controller;

import javafx.event.ActionEvent;

/**
 * All methods needed for running a Tournament
 * @author boscalent
 */
public interface RunningTournament {
    void onPublish(ActionEvent event);
    void onCancelPublished(ActionEvent event);
    void onClose(ActionEvent event);
    void onBegin(ActionEvent event);
    void onCancelClose(ActionEvent event);
    void onFinished(ActionEvent event);
    void onInterrup(ActionEvent event);
    void onRunning(ActionEvent event);
    void publishResults(ActionEvent event);
    void onRestartPublish(ActionEvent event);
    void onRestartClose(ActionEvent event);
    void onCancelInterrupted(ActionEvent event);
    void onRestartBeginned(ActionEvent event);
}
