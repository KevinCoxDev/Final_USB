Function GET-DISKPARTINFO() 
{ 
   
     
    new-item -Name listdisk.txt -Itemtype file -force | out-null 
    add-content -path listdisk.txt "list disk" 
    $listdisk=(diskpart /s listdisk.txt)  
     
    $totaldisk=$listdisk.count-9
    $myArray = New-Object System.Collections.ArrayList
     
  
    for ($d=0;$d -lt $totaldisk+1;$d++) 
    { 
     
        $size=$listdisk[8+$d].substring(25,9).replace(" ","") 
        $diskid=$listdisk[8+$d].substring(7,5).trim()
     
        new-item -Name detail.txt -ItemType file -force | out-null 
        add-content -Path detail.txt "select disk $diskid" 
        add-content -Path detail.txt "detail disk" 
         
        # Capture the output from Diskpart for the Detail 
         
        $Detail=(diskpart /s detail.txt) 
         
        # Parse the data for the partition 
         
        $Model=$detail[8] 
        $type=$detail[9].substring(9) 
        $DriveLetter=$detail[-1].substring(15,1) 
         
        # Grab the partition sizing data 
         
        $length=$size.length 
        $multiplier=$size.substring($length-2,2) 
        $intsize=$size.substring(0,$length-2) 
         
        
        # Calculate the size of the Disk or Partition 
        #$disktotal=([convert]::ToInt16($intsize,10))*$mult


        #Check if the devie is USB or not
        if($type -ceq "USB"){# -And $size -lt 10000MB){
           $myArray += $diskid
        }

     
    }
    return $myArray
}

GET-DISKPARTINFO