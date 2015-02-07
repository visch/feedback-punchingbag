#
_XDCBUILDCOUNT = 
ifneq (,$(findstring path,$(_USEXDCENV_)))
override XDCPATH = C:/ti/grace/grace_2_20_02_32/packages;C:/Users/BigalsLaptop/workspace_grace_2_1/FullPFPB/.config
override XDCROOT = C:/ti/grace/xdctools_3_25_04_88
override XDCBUILDCFG = ./config.bld
endif
ifneq (,$(findstring args,$(_USEXDCENV_)))
override XDCARGS = 
override XDCTARGETS = 
endif
#
ifeq (0,1)
PKGPATH = C:/ti/grace/grace_2_20_02_32/packages;C:/Users/BigalsLaptop/workspace_grace_2_1/FullPFPB/.config;C:/ti/grace/xdctools_3_25_04_88/packages;..
HOSTOS = Windows
endif
