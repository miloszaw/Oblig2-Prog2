Changed the constructors of the BonusMember, BasicMember, SilverMember and GoldMember 
to now also have a fourth parameter which describes the amount of points they are 
automatically registered with.

Changed the way the qualification points are determined; 
created an ArrayList of the class Points which features amount of points registered and the date they were registered on
qualifying points from now on are points which have been registered within 365 of the date given to the findQualificationPoints() method. 
Check out the changes made to the methods; 
getBonuspointList() in the BonusMember class, 
updatePoints() in the BonusMember class, 
registerPoints() in the classes BonusMember and MemberArchive, 
and checkMembers() in the MemberArchive class.
