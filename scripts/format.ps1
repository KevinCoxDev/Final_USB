Function JSON()
{
    
    $content = Get-Content -Raw -Path Output.json | ConvertFrom-Json
    return $content


}

Function INVOKE-USBBOOT() 
{  
	New-Item -Path bootdown.txt -ItemType file -force | OUT-NULL
    New-Item -Path bootemup.txt -ItemType file -force | OUT-NULL
    DISKPART_MOUNT_SCRIPT
    DISKPART /S bootemup.txt

 
}

Function DISKPART_MOUNT_SCRIPT() 
{ 
    $DiskNum = JSON
    $DiskLetter = ($a = "Q","T","U","V","X","Z") | Get-Random
    ADD-CONTENT -Path bootemup.txt -Value "SELECT DISK $DiskNum"
    ADD-CONTENT -Path bootemup.txt -Value "ASSIGN LETTER=$DiskLet"
   
}

INVOKE-USBBOOT