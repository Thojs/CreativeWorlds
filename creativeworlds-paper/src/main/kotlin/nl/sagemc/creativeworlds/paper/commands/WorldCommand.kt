package nl.sagemc.creativeworlds.paper.commands

import nl.sagemc.creativeworlds.api.commandhandler.Command
import nl.sagemc.creativeworlds.paper.commands.world.*
import org.bukkit.command.CommandSender

object WorldCommand : Command<CommandSender>("world", "w", "plot", "p") {
    init {
        command {
            appendArguments(
                CreateArgument, // DONE
                HomeArgument, // DONE
                InfoArgument,
                TrustArgument, // DONE
                MemberArgument, // DONE
                FlagArgument,
                DenyArgument,
                AliasArgument, // DONE
                VisitArgument,
                DeleteArgument,
                SetSpawnArgument // DONE
            )
        }
    }
}