cls
echo ===============================================================================================
echo =  mavenCentral Publishing Tool v1.0                                                          =
echo =  written by Carl Williams [TheRandomCrafter83]                                              =
echo ===============================================================================================
echo =    Beginning Publish to MavenCentral                                                        =
updateversion.exe %1
gradlew CustomDialogs:publishReleasePublicationToSonatypeRepository
echo =    Done                                                                                     =
echo ===============================================================================================