package me.thojs.creativeworlds.paper.commands.exceptions

class WorldLimitReachedException(worldLimit: Int): CreativeWorldsException("You have reached your world creation limit of ${worldLimit}!")