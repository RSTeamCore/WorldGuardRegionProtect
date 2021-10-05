package net.ritasister.command;

class CommandTest : TabExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        if (args.size != 1)
        {
            return false
        }
        
    }
}