package adventOfCode.day04

import java.text.SimpleDateFormat
import adventOfCode.util.AbstractChallange
import adventOfCode.day04.Guard

class SleepingGuard extends AbstractChallange {
    private Map<Integer, Guard> guards
    
    SleepingGuard() {
        day = 4
        
        def format = new SimpleDateFormat("yyyy-MM-dd hh:mm")
        def records = data.collect{
            def tmp = (it =~ /^\[([^\]]+)\]\s(.*)$/).getAt(0).drop(1)
            new Tuple(format.parse(tmp[0]), tmp[1])
        }.toSorted { a, b -> a[0] <=> b[0] }

        guards = [:]
        def currentGuard = null
        def start = 0;

        for(def i = 0; i < records.size(); i++) {
            def dateTime = records[i][0]
            def text = records[i][1]

            if(text.startsWith('Guard')) {
                def id = (text =~ /#(\d+)/).getAt(0)[1]
                def guard = guards.get(id)
                if(guard) {
                    currentGuard = guard
                } else {
                    currentGuard = new Guard(id)
                    guards.put(id, currentGuard)
                }
            } else if(text == 'falls asleep') {
                start = dateTime.getMinutes()
            } else {
                currentGuard.addSleepTime(start, dateTime.getMinutes())
            }
        }
    }

    String solution1() {
        def sleepy = guards.max { it.value.getTotalSleepTime() }.value
        def minute = sleepy.getMostSleepMinute()
        return sleepy.id * minute;
    }

    String solution2() {
        def sleepy = guards.max { it.value.getMostSleepMinuteCount() }.value
        def minute = sleepy.getMostSleepMinute()
        return sleepy.id * minute;
    }
}
