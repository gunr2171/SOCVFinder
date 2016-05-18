package jdd.so.bot.actions.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.tunaki.stackoverflow.chat.Room;
import fr.tunaki.stackoverflow.chat.event.PingMessageEvent;
import jdd.so.CloseVoteFinder;
import jdd.so.bot.actions.BotCommand;
import jdd.so.model.User;

public class UserListCommand extends  BotCommand{

	@Override
	public String getMatchCommandRegex() {
		return "(?i)(users|user list)";
	}

	@Override
	public int getRequiredAccessLevel() {
		return BotCommand.ACCESS_LEVEL_NONE;
	}

	@Override
	public String getCommandName() {
		return "User list";
	}

	@Override
	public String getCommandDescription() {
		return "Display the list of enabled users";
	}

	@Override
	public String getCommandUsage() {
		return "users";
	}

	@Override
	public void runCommand(Room room, PingMessageEvent event) {
		List<User> users = new ArrayList<>();
		users.addAll(CloseVoteFinder.getInstance().getUsers().values());
		Collections.sort(users);
		room.replyTo(event.getMessageId(), "These are current users");
		StringBuilder retMsg = new StringBuilder("");
		int al = -1;
		for (User user : users) {
			if (user.getAccessLevel()<=0){
				continue;
			}
			if (user.getAccessLevel()!=al){
				if (al>-1){
					retMsg.append("\n");	
				}
				al = user.getAccessLevel();
				
				retMsg.append("    " + BotCommand.getAccessLevelName(al));
			}
			retMsg.append("\n        " +user.getUserId() + " - " + user.getUserName());
		}
		retMsg.append("");
		room.send(retMsg.toString());
	}
	
	

}