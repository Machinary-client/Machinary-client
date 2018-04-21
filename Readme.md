This application simulated the work of machine, show special information about inputted commands.
It can show:
* Videos
* Images

You can configure the application by written config file: `config.cfg`.
This file should be in the same directory with `*.jar` file.

In `config.cfg` you can write `host` - host for connection and `port` - port for connection.
Default `host = localhost`, `port = 5555`.

**Attention:** file `config.cfg` should exists

All your technical processes must be in folder `processes` that should be in the same directory with `*.jar` file

Folder processes must have file `common.cfg` - common (default) parameters for all processes
 
 Required parameters:
* `process_switcher` - predicate in command for switching to another process

Optional parameters:
* `technology_switcher` - predicate in command for switching technology
* `image_delay` - time in ms of showing image - default 0
* `video_delay` - time in ms for waiting after showing video - default 0
* `unknown_command` - what to do if program does not have information about inputted command 
 
All lines (part of lines) starts with `#` will be omitted, but you can use `\` before this symbol.

Any process in folder `processes` is also the folder, that in least have file `default.tech`. In this file you can write 
common commands for process, like `technology_switcher` or `image_deley` or `video_delay`

**Attention:** All technology files in process must ended with `.tech`

When you ask the program to switch process it works like that:
1. you send a command <process_switcher> <process_name>
2. program find process with <process_name> in processes
3. read file `default.tech`

When you ask to switch technology:
1. You send a command <technology_switcher> <technology_name>
2. program find and read file <technology_name>.tech

You can use following syntax in files `commo.cfg`, `*.tech`:
* `#` - for comments
* `technology_switcher` - predicate in command for switching technology
* `image_delay` - time in ms of showing image - default 0 or from `default.tech` or from `common.cfg`
* `video_delay` - time in ms for waiting after showing video - default 0 or from `default.tech` or from `common.cfg`
* `unknown_command` - what to do if program does not have information about inputted command 
* "your command": "path to executing file (*.mp4, *.jpg, *.jpeg, *.png)"
* "your command": action (exit, fail, skip, crush)

Use `\` for input `"`, `'` or `#` in quotes 
