rm -f $1/Annotation_Errors.txt
outfile="$1/Annotation_Errors.txt"

#for file in `ls $2/full*.prun.out`
#for file in `ls $2/full*`
for file in `ls $2/full*.dat $2/full*.prun`
do
	#echo ""
        sed -e 's/document id/document docid/g' -i $file
        sed -e 's/<Sentence id=.\(.*\).>/<Sentence id="\1">/g' -i $file
	perl $1/pos_checks.pl $file $1 >> $outfile
	perl $1/chunk_checks_count.pl $file $1 >> $outfile
	perl $1/tree_checks.pl $file $1 >> $outfile
	perl $1/linguistic_checks_count.pl $file $1 >> $outfile
	#echo ""
done

