## THIS IS A GENERATED FILE -- DO NOT EDIT
.configuro: .libraries,430 linker.cmd package/cfg/FullPFPB_p430.o430

linker.cmd: package/cfg/FullPFPB_p430.xdl
	$(SED) 's"^\"\(package/cfg/FullPFPB_p430cfg.cmd\)\"$""\"C:/Users/BigalsLaptop/workspace_grace_2_1/FullPFPB/.config/xconfig_FullPFPB/\1\""' package/cfg/FullPFPB_p430.xdl > $@
