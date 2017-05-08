###################################################
How to Make USB BOOT drive
###################################################
1. Plug-in USB drive to PC.

2. Format UDB drive using window utilities.
   Select the USB drive. Right click on the mouse to select "Format".

3. Note: Duov2 and NV+v2 without this step.
   Make USB disk bootable under linux. 
   Open a "CMD" DOS window. 
   Run "syslinux.exe -m -a [DRIVE]:" command, where [DRIVE] is the USB disk reference.

4. Copy USBBOOT software to the USB drive. 
   Depending on target DUT, you can copy the following files from 
   
   USBBOOT-x86 , USBBOOT-x86-3.0 or USBBOOT-Tolapai Directory:
      * initrd.gz
      * kernel
      * kernel.mp
      * kernel.up
      * syslinux.cfg
	 
   USBBOOT-arm-XXX Directory:
      * initrd-recovery.gz
      * uImage-recovery
  
 [Note]:
    - Skip the third step for the USB BOOT drive of Duo V2 (RND2000-V3) & NV+ V2 (RND4000-V4).
    - Use USBBOOT-Tolepai for NVX (RNDX4000-V1) & 2100 (RNRX4000-V1).
    - Use USBBOOT-arm-Duo for Duo V2 (RND2000-V3).
    - Use USBBOOT-arm-NV  for NV+ V2 (RND4000-V4).
    - Use USBBOOT-arm-3.0 for RN102, RN104, RN2120.
    - Use USBBOOT-arm-r6-rn200 for RN202, RN204.^M
    - Use USBBOOT-x86-3.0 for RN312, RN314, RN316, RN516, RN716, RN3130.
    - Use USBBOOT-x86 for the rest of SKUs.


NETGEAR, Inc.		Confidential



ReadyNAS System Manufacturing Instruction                                                                    Page 27 of 27
		

